package com.impinj;

import com.example.sdksamples.TagReportListenerImplementation;
import com.impinj.octane.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * 读取标签
 */
public class ReadTags {

    private static final Logger log = LoggerFactory.getLogger(ReadTags.class);

    public static void main(String[] args) {
        try {
            ImpinjReader impinjReader = ImpinjReaderSingleton.getConnectedImpinjReader();

            // TODO: 2020/5/16 读取标签设置
            // TODO: 2020/5/16 天线设置 暂时先单个模块，后续进行抽取共通方法

            Settings settings = impinjReader.queryDefaultSettings();
            ReportConfig report = settings.getReport();
            // 报告中包含天线端口号
            report.setIncludeAntennaPortNumber(true);
            // 在Individual报告模式下，阅读器会将每个标签观察结果作为单独的报告发送。
            report.setMode(ReportMode.Individual);
            // 在AutoSetDenseReader读写器模式下，监视RF噪声和干扰，然后自动并持续优化阅读器的配置
            settings.setReaderMode(ReaderMode.AutoSetDenseReader);

            // 设置搜索模式。进入扫描区域为A状态，离开后为B状态
            settings.setSearchMode(SearchMode.SingleTargetReset);
            settings.setSession(2);


            AntennaConfigGroup antennaConfigs = settings.getAntennas();

            log.info("当前读写器有"+ antennaConfigs.getAntennaConfigs().size() + "个天线");

            // 禁用全部
            antennaConfigs.disableAll();
            antennaConfigs.enableById(new short[]{1});
            // 取消最大灵敏度
            antennaConfigs.getAntenna((short) 1).setIsMaxRxSensitivity(false);
            // 取消最大功率
            antennaConfigs.getAntenna((short) 1).setIsMaxTxPower(false);
            // 设置功率
            antennaConfigs.getAntenna((short) 1).setTxPowerinDbm(20.0);
            // 设置灵敏度
            antennaConfigs.getAntenna((short) 1).setRxSensitivityinDbm(-70);

            impinjReader.setTagReportListener(new TagReportListenerImplementation());

            // 启用参数设置
            impinjReader.applySettings(settings);

            ImpinjReaderSingleton.start();


            System.out.println("Press Enter to exit.");
            Scanner s = new Scanner(System.in);
            s.nextLine();

            ImpinjReaderSingleton.stop();


            ImpinjReaderSingleton.disConnect();

        }catch (Throwable e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }
}
