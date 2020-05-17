package com.impinj;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 保存自定义设置为 默认设置
public class SaveDefaultSettingForSetting {

    private static final Logger log = LoggerFactory.getLogger(SaveDefaultSettingForSetting.class);

    public static void main(String[] args) {

        try {
            ImpinjReader impinjReader = ImpinjReaderSingleton.getConnectedImpinjReader();
            if (null == impinjReader) {
                // TODO: 2020/5/16 添加提醒手段
                throw new Exception("无法获取Impinj Reader实例，若持续了一段时间，请联系管理员");
            }

            SaveDefaultSettingForSetting saveDefaultSettingForSetting = new SaveDefaultSettingForSetting();
            saveDefaultSettingForSetting.restorSetting(impinjReader);

            impinjReader.disconnect();
            log.info("Impinj Reader hostname:" + impinjReader.getAddress() + "断开连接！！");

        } catch (OctaneSdkException e) {
            e.printStackTrace();
            log.error("操作 impinj reader 异常！！！", e);
        }catch (Throwable e) {
            e.printStackTrace();
            log.error("操作 impinj reader 异常！！！", e);
        }

    }

    /**
     * 重置设置
     * 重置为默认设置
     * @param impinjReader
     */
    private void restorSetting(ImpinjReader impinjReader) throws OctaneSdkException {
        impinjReader.applySettings(impinjReader.queryDefaultSettings());
        impinjReader.saveSettings();
        log.info("自定义设置重置为重置为默认设置  成功！！");
    }
}
