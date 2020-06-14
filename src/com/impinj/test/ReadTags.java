package com.impinj.test;

import com.example.sdksamples.SampleProperties;
import com.example.sdksamples.TagReportListenerImplementation;
import com.impinj.ImpinjReaderSingleton;
import com.impinj.octane.*;

import java.util.Scanner;


public class ReadTags {

    public static void main(String[] args) {

        try {
//            String hostname = System.getProperty(SampleProperties.hostname);
//
//            if (hostname == null) {
//                throw new Exception("Must specify the '"
//                        + SampleProperties.hostname + "' property");
//            }

            ImpinjReader reader = ImpinjReaderSingleton.getConnectedImpinjReader();

//            System.out.println("Connecting");
//            reader.connect(hostname);

            Settings settings = reader.queryDefaultSettings();

            ReportConfig report = settings.getReport();
            report.setIncludeFirstSeenTime(true);
            report.setIncludeAntennaPortNumber(true);
            report.setMode(ReportMode.Individual);

            // The reader can be set into various modes in which reader
            // dynamics are optimized for specific regions and environments.
            // The following mode, AutoSetDenseReader, monitors RF noise and interference and then automatically
            // and continuously optimizes the reader's configuration
            // 可以将阅读器设置为各种模式，其中阅读器动态针对特定区域和环境进行了优化
            // 以下模式AutoSetDenseReader监视RF噪声和干扰，然后自动并持续优化阅读器的配置
            settings.setReaderMode(ReaderMode.AutoSetDenseReader);

            // set some special settings for antenna 1
            // 为天线1设置一些特殊设置
            AntennaConfigGroup antennas = settings.getAntennas();
            antennas.disableAll();
            antennas.enableById(new short[]{1});
            antennas.getAntenna((short) 1).setIsMaxRxSensitivity(false);
            antennas.getAntenna((short) 1).setIsMaxTxPower(false);
            antennas.getAntenna((short) 1).setTxPowerinDbm(20.0);
            antennas.getAntenna((short) 1).setRxSensitivityinDbm(-70);

            reader.setTagReportListener(new TagReportListenerImplementation());

            System.out.println("Applying Settings");
            reader.applySettings(settings);

            System.out.println("Starting");
            reader.start();

            System.out.println("Press Enter to exit.");
            Scanner s = new Scanner(System.in);
            s.nextLine();

            reader.stop();
            reader.disconnect();
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
