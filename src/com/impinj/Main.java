package com.impinj;

import com.impinj.octane.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try {
            ImpinjReader impinjReader = ImpinjReaderSingleton.getConnectedImpinjReader();

//            while (null == impinjReader) {
//                // TODO: 2020/5/16 添加提醒手段
//                throw new Exception("无法获取Impinj Reader实例，若持续了一段时间，请联系管理员");
//            }

            Settings settings = impinjReader.queryDefaultSettings();

            ReaderMode readerMode = settings.getReaderMode();
            log.info("默认设置中的ReaderMode是：" + readerMode);
            ReportMode mode = settings.getReport().getMode();
            log.info("默认设置中的Report是：" + mode);

            settings.getReport().setMode(ReportMode.WaitForQuery);
            impinjReader.applySettings(settings);

            ReportMode newMode = impinjReader.querySettings().getReport().getMode();
            log.info("设置后的中的Report是：" + newMode);


//            log.info("读写器保存自定义设置");
//            impinjReader.saveSettings();

            log.info("读写器断开连接");
            impinjReader.disconnect();

            log.info("读写器再次连接");
            impinjReader = ImpinjReaderSingleton.getConnectedImpinjReader();

            while (null == impinjReader) {
                // TODO: 2020/5/16 添加提醒手段
                throw new Exception("无法获取Impinj Reader实例，若持续了一段时间，请联系管理员");
            }

            log.info("设置后的设置中的Report是：" + impinjReader.querySettings().getReport().getMode());
            log.info("设置后的默认设置中的Report是：" + impinjReader.queryDefaultSettings().getReport().getMode());

            impinjReader.disconnect();
            log.info("Impinj Reader hostname:" + impinjReader.getAddress() + " name: "+ impinjReader.getName() +"断开连接！！");

        } catch (OctaneSdkException e) {
            e.printStackTrace();
            log.error("操作 impinj reader 异常！！！", e);
        }catch (Throwable e) {
            e.printStackTrace();
            log.error("操作 impinj reader 异常！！！", e);
        }

    }



}
