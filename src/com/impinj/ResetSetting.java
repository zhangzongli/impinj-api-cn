package com.impinj;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Settings;

/**
 * @program: impinj-api-cn
 * @description: 重置设置
 * @author: zhangzl
 * @create: 2020-06-14 23:19
 **/
public class ResetSetting {

    public static void main(String[] args) throws Throwable {
        ImpinjReader reader = ImpinjReaderSingleton.getConnectedImpinjReader();

        Settings settings = reader.queryDefaultSettings();

        reader.applySettings(settings);

        ImpinjReaderSingleton.disConnect();


    }
}
