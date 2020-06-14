package com.impinj;

import com.example.sdksamples.TagReportListenerImplementation;
import com.impinj.cache.ReadTagsDataCache;
import com.impinj.cache.TagsDataCache;
import com.impinj.listener.TagReportListenerImpl;
import com.impinj.octane.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
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

            // 设置搜索模式。不停读取标签 AB 状态循环
            settings.setSearchMode(SearchMode.DualTarget);
            settings.setSession(0);
//            settings.setSearchMode(SearchMode.SingleTarget);
//            settings.setSession(1);

            AntennaConfigGroup antennaConfigs = settings.getAntennas();

            log.info("当前读写器有"+ antennaConfigs.getAntennaConfigs().size() + "个天线");

            // 启用全部
            antennaConfigs.disableAll();
            antennaConfigs.enableById(new short[]{3});
            // 取消最大灵敏度
            antennaConfigs.getAntenna((short) 3).setIsMaxRxSensitivity(false);
            // 取消最大功率
            antennaConfigs.getAntenna((short) 3).setIsMaxTxPower(false);
            // 设置功率
            antennaConfigs.getAntenna((short) 3).setTxPowerinDbm(20.0);
            // 设置灵敏度
            antennaConfigs.getAntenna((short) 3).setRxSensitivityinDbm(-70);
            antennaConfigs.enableById(new short[]{2});
            // 取消最大灵敏度
            antennaConfigs.getAntenna((short) 2).setIsMaxRxSensitivity(false);
            // 取消最大功率
            antennaConfigs.getAntenna((short) 2).setIsMaxTxPower(false);
            // 设置功率
            antennaConfigs.getAntenna((short) 2).setTxPowerinDbm(20.0);
            // 设置灵敏度
            antennaConfigs.getAntenna((short) 2).setRxSensitivityinDbm(-70);
//            for (int i = 1; i <= antennaConfigs.getAntennaConfigs().size(); i++) {
//                AntennaConfig antennaConfig = antennaConfigs.getAntenna((short) i);
//                // 取消最大灵敏度
//                antennaConfigs.getAntenna((short) 1).setIsMaxRxSensitivity(false);
//                // 取消最大功率
//                antennaConfigs.getAntenna((short) 1).setIsMaxTxPower(false);
//                // 设置功率
//                antennaConfigs.getAntenna((short) 1).setTxPowerinDbm(20.0);
//                // 设置灵敏度
//                antennaConfigs.getAntenna((short) 1).setRxSensitivityinDbm(-70);
//            }

            impinjReader.setTagReportListener(new TagReportListenerImpl());

            // 报告中包括 TID
            settings.getReport().setIncludeFastId(true);

            // 启用参数设置
            impinjReader.applySettings(settings);

            // 缓存清空
            TagsDataCache.clearAll();

            ImpinjReaderSingleton.start();


            System.out.println("Press Enter to show CacheData.");
            Scanner s = new Scanner(System.in);
            s.nextLine();

//            for (Object pageShowDatum : ReadTagsDataCache.getPageShowData()) {
//                log.info("返回结果：" + pageShowDatum.toString());
//            }

            log.info(TagsDataCache.getDirection());

            System.out.println("Press Enter to exit.");
            Scanner s1 = new Scanner(System.in);
            s1.nextLine();

            ImpinjReaderSingleton.stop();

            ImpinjReaderSingleton.disConnect();

        }catch (Throwable e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }
}
