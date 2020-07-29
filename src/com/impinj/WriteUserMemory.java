package com.impinj;

import com.example.sdksamples.SampleProperties;
import com.example.sdksamples.TagOpCompleteListenerImplementation;
import com.example.sdksamples.TagReportListenerImplementation;
import com.impinj.octane.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Scanner;

public class WriteUserMemory {

    private static final Logger log = LoggerFactory.getLogger(WriteUserMemory.class);


    String defaultWrite = "abcd0123";

    public static void main(String[] args) {
        try {
            ImpinjReader reader = ImpinjReaderSingleton.getConnectedImpinjReader();

            // Get the default settings
            Settings settings = reader.queryDefaultSettings();

            settings.getReport().setIncludeAntennaPortNumber(true);

            AntennaConfigGroup antennaConfigs = settings.getAntennas();

            log.info("当前读写器有"+ antennaConfigs.getAntennaConfigs().size() + "个天线");

            // 设置搜索模式。不停读取标签 AB 状态循环
            settings.setSearchMode(SearchMode.DualTarget);
            settings.setSession(0);

            antennaConfigs.disableAll();
            antennaConfigs.enableById(new short[]{2});
            // 取消最大灵敏度
            antennaConfigs.getAntenna((short) 2).setIsMaxRxSensitivity(false);
            // 取消最大功率
            antennaConfigs.getAntenna((short) 2).setIsMaxTxPower(false);
            // 设置功率
            antennaConfigs.getAntenna((short) 2).setTxPowerinDbm(10.0);
            // 设置灵敏度
            antennaConfigs.getAntenna((short) 2).setRxSensitivityinDbm(-70);

            // Apply the new settings
            reader.applySettings(settings);

            // create the reader op sequence
            TagOpSequence seq = new TagOpSequence();
            seq.setOps(new ArrayList<TagOp>());
            seq.setExecutionCount((short) 1); // forever
            seq.setState(SequenceState.Active);
            seq.setId(1);

            TagWriteOp writeOp = new TagWriteOp();
            writeOp.setMemoryBank(MemoryBank.User);
            writeOp.setWordPointer((short) 0);
            writeOp.setData(TagData.fromHexString("052080000000000000000011"));
//            writeOp.setData(TagData.fromHexString("123456789"));

            // add to the list
            seq.getOps().add(writeOp);

            // Use target tag to only apply to some EPCs
            String targetEpc = System.getProperty(SampleProperties.targetTag);

            if (targetEpc != null) {
                seq.setTargetTag(new TargetTag());
                seq.getTargetTag().setBitPointer(BitPointers.Epc);
                seq.getTargetTag().setMemoryBank(MemoryBank.Epc);
                seq.getTargetTag().setData(targetEpc);
            } else {
                // or just send NULL to apply to all tags
                seq.setTargetTag(null);
            }

            // add to the reader. The reader supports multiple sequences
            reader.addOpSequence(seq);

            // set up listeners to hear stuff back from SDK. Normally the 
            // application would enable this but we dont want too many 
            // reports in our example
            reader.setTagReportListener(new TagReportListenerImplementation());

            reader.setTagOpCompleteListener(
                    new TagOpCompleteListenerImplementation());

            // Start the reader
            reader.start();

            System.out.println("Press Enter to exit.");
            Scanner s = new Scanner(System.in);
            s.nextLine();

            ImpinjReaderSingleton.stop();

            ImpinjReaderSingleton.disConnect();

            System.out.println("Done");
        } catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
