package com.example.sdksamples;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.Settings;

import java.util.Scanner;


// 保持读写器存活
public class Keepalives {

    public static void main(String[] args) {
        try {

            String hostname = System.getProperty(SampleProperties.hostname);

            if (hostname == null) {
                throw new Exception("Must specify the '"
                        + SampleProperties.hostname + "' property");
            }

            ImpinjReader reader = new ImpinjReader();

            System.out.println("Connecting");
            reader.connect(hostname);

            Settings settings = reader.queryDefaultSettings();

            // turn on the keepalives
            // 打开keepalive
            settings.getKeepalives().setEnabled(true);
            // 读者保持连接之间的间隔时间
            settings.getKeepalives().setPeriodInMs(3000);

            // turn on automatic link monitoring
            // 打开自动链接监控
            // 在链接监视模式下，读取器将监视来自SDk的活动响应，并在丢失时关闭连接。
            settings.getKeepalives().setEnableLinkMonitorMode(true);
            // 在链接监视模式下，此设置确定在关闭连接之前可以错过多少个存活时间。
            settings.getKeepalives().setLinkDownThreshold(5);

            // set up a listener for keepalives
            // 设置保持存活的监听
            reader.setKeepaliveListener(new KeepAliveListenerImplementation());

            // set up a listener for connection Lost
            // 设置连接丢失的监听
            reader.setConnectionLostListener(
                    new ConnectionLostListenerImplementation());

            // apply the settings to enable keepalives
            reader.applySettings(settings);

            System.out.println("Press Enter to exit.");
            Scanner s = new Scanner(System.in);
            s.nextLine();

            reader.disconnect();
        } catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}
