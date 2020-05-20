package com.impinj.test;

import com.example.sdksamples.TagReportListenerImplementation;
import com.impinj.ImpinjReaderSingleton;
import com.impinj.octane.AntennaConfigGroup;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Settings;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 天线相关测试
 */
public class AntennaTest {

    public static void main(String[] args) {

        try {
            ImpinjReader impinjReader = ImpinjReaderSingleton.getConnectedImpinjReader();
            Settings settings = impinjReader.queryDefaultSettings();

            AntennaConfigGroup antennas = settings.getAntennas();

            antennas.disableAll();
            short portNumber = 4;
            antennas.enableById(new short[]{4});
            // 取消最大灵敏度
            antennas.getAntenna(portNumber).setIsMaxRxSensitivity(false);
            // 取消最大功率
            antennas.getAntenna(portNumber).setIsMaxTxPower(false);
            // 设置功率
            antennas.getAntenna(portNumber).setTxPowerinDbm(20.0);
            // 设置灵敏度
            antennas.getAntenna(portNumber).setRxSensitivityinDbm(-70);

            impinjReader.setTagReportListener(new TagReportListenerImplementation());

            impinjReader.applySettings(settings);

            ImpinjReaderSingleton.start();

            System.out.println("Press Enter to exit.");
            Scanner s = new Scanner(System.in);
            s.nextLine();

            ImpinjReaderSingleton.stop();

            ImpinjReaderSingleton.disConnect();


        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


}
