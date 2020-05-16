package com.example.sdksamples;

import com.impinj.octane.*;

import java.util.Scanner;

public class SetReaderModes {

    public static void main(String[] args) {

        try {
            String hostname = System.getProperty(SampleProperties.hostname);

            if (hostname == null) {
                throw new Exception("Must specify the '"
                        + SampleProperties.hostname + "' property");
            }

            ImpinjReader reader = new ImpinjReader();

            reader.connect(hostname);

            Settings settings = reader.queryDefaultSettings();

            ReportConfig r = settings.getReport();

            // tell the reader to include the antenna port number in the report
            // 告诉读写器在报告中包括天线端口号
            r.setIncludeAntennaPortNumber(true);
            r.setIncludeFirstSeenTime(false);

            // ask for a report for each tag read
            // 要求为每个读取的标签提供报告
            r.setMode(ReportMode.Individual);

            // don't forget to set these back again
            // 不要忘记再次设置这些
            settings.setReport(r);

            // set up the gen2 parameters of interest
            // 设置感兴趣的gen2参数
            settings.setReaderMode(ReaderMode.AutoSetDenseReader);
            settings.setSearchMode(SearchMode.DualTarget);
            settings.setSession(2);
            settings.setTagPopulationEstimate(32);

            // you must apply these settings for them to appear
            // 您必须应用这些设置才能显示
            reader.applySettings(settings);

            reader.setTagReportListener(new TagReportListenerImplementation());

            reader.start();

            System.out.println("Press enter to exit.");
            Scanner s = new Scanner(System.in);
            s.nextLine();

            reader.stop();

            reader.disconnect();
        } catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}
