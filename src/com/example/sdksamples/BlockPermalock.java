package com.example.sdksamples;

import com.impinj.octane.*;

import java.util.ArrayList;
import java.util.Scanner;


// demonstrates block permalocking of user memory
// 演示用户内存的块永久锁定
public class BlockPermalock {

    public static void main(String[] args) {
        try {
            String hostname = System.getProperty(SampleProperties.hostname);

            if (hostname == null) {
                throw new Exception("Must specify the '"
                        + SampleProperties.hostname + "' property");
            }

            ImpinjReader reader = new ImpinjReader();

            // Connect
            System.out.println("Connecting to " + hostname);
            reader.connect(hostname);

            // Get the default settings
            Settings settings = reader.queryDefaultSettings();

            settings.getReport().setIncludeAntennaPortNumber(true);

            // Apply the new settings
            reader.applySettings(settings);

            // create the reader op sequence
            // 创建一个读写器操作序列
            TagOpSequence seq = new TagOpSequence();
            seq.setOps(new ArrayList<TagOp>());
            // 此操作序列在被删除之前将执行的次数。零（0）值表示该序列永远不会删除。
            seq.setExecutionCount((short) 1);
            // 徐磊状态
            seq.setState(SequenceState.Active);
            // 设置id
            seq.setId(1);

            // lock the first block of memory. This only works on user memory
            TagBlockPermalockOp blockOp = new TagBlockPermalockOp();
            blockOp.setBlockMask(BlockPermalockMask.fromBlockNumber((short) 0));

            // add to the list
            seq.getOps().add(blockOp);

            String targetTag = System.getProperty("targetTag");

            // since its not reversible, we make the example set this property
            if (targetTag != null) {
                seq.setTargetTag(new TargetTag());
                seq.getTargetTag().setBitPointer((short) 32);
                seq.getTargetTag().setMemoryBank(MemoryBank.Epc);
                seq.getTargetTag().setData(targetTag);
            } else {
                throw new Exception("You must specify the 'targetTag' property "
                        + "for this example as the block permalock command "
                        + "is not reversible");
            }
            // add to the reader. The reader supports multiple sequences
            reader.addOpSequence(seq);

            // set up the listener for the tag operation
            reader.setTagOpCompleteListener(
                    new TagOpCompleteListenerImplementation());

            // typically the application would also listen for tag reports
            // but we don't here since it would print out too much
            // reader.setTagReportListener(new TagReportListenerImplementation());

            // 通常，应用程序还会侦听标签报告，但是我们不在这里，因为它会打印出过多的阅读器。
            // setTagReportListener（new TagReportListenerImplementation（））;

            // Start the reader
            reader.start();

            System.out.println("Press Enter to exit.");
            Scanner s = new Scanner(System.in);
            s.nextLine();

            System.out.println("Stopping  " + hostname);
            reader.stop();

            System.out.println("Disconnecting from " + hostname);
            reader.disconnect();

            System.out.println("Done");
        } catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}
