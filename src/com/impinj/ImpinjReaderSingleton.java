package com.impinj;

import com.impinj.exception.ErrorStartException;
import com.impinj.exception.ErrorStopException;
import com.impinj.exception.UnableToConnectException;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取已经连接的Impinj Reader实例
 */
public class ImpinjReaderSingleton {

    private static final Logger log = LoggerFactory.getLogger(ImpinjReaderSingleton.class);

    /**
     * Impinj Reader 实例
     */
    private volatile static ImpinjReader impinjReader = null;

    /** 重试次数 */
    private static int retryNum = 0;

    private static final String hostname = "169.254.1.1";
//    private static final String hostname = "192.168.31.200";


    ImpinjReaderSingleton() {}

    public static ImpinjReader getConnectedImpinjReader() throws Throwable {

        if (null == hostname || "".equals(hostname)) {
            log.error("读写器地址hostname不能为空！！");
            return null;
        }

        if (null == impinjReader || !impinjReader.isConnected()) {
            synchronized (ImpinjReaderSingleton.class) {

                if (null == impinjReader || !impinjReader.isConnected()) {
                    try {
                        log.info("开始连接Impinj Reader hostname:" + hostname);
                        impinjReader = impinjReader == null ? new ImpinjReader() : impinjReader;
                        // TODO: 2020/5/16 连接超时 时间暂时默认
                        impinjReader.connect(hostname);

                        // TODO: 2020/5/16 添加 输出自定义配置等。
                        log.info("连接Impinj Reader hostname:" + hostname + "成功！！");

                        // 重试次数清零
                        retryNum = 0;
                    }catch (Exception e) {
                        // TODO: 2020/5/16 添加提醒手段
                        e.printStackTrace();
                        log.error("连接读写器出现异常. hostname:" + hostname);
                        log.error("若重试次数过多，请联系管理员");
                        log.error("连接读写器出现异常. 第"+ (retryNum + 1) +"次重新尝试连接. hostname:" + hostname);
                        retryNum++;
                        getConnectedImpinjReader();
                        throw new Exception();
                    }
                }
            }
        }
        return impinjReader;
    }

    /**
     * 开启读写器
     * @throws ErrorStartException 连接错误异常
     */
    public static void start() throws ErrorStartException {
        try {
            impinjReader.start();
        } catch (OctaneSdkException e) {
            e.printStackTrace();
            String msg = String.format(String.format("Impinj Reader hostname: %s name: %s 启动出现异常！！", impinjReader.getAddress(), impinjReader.getName()));
            log.error(msg, e);
            throw new ErrorStartException(msg);
        }
    }

    /**
     * 关闭读写器
     * @throws ErrorStartException 连接错误异常
     */
    public static void stop() throws ErrorStopException {
        try {
            impinjReader.stop();
        } catch (OctaneSdkException e) {
            e.printStackTrace();
            String msg = String.format(String.format("Impinj Reader hostname: %s name: %s 关闭出现异常！！", impinjReader.getAddress(), impinjReader.getName()));
            log.error(msg, e);
            throw new ErrorStopException(msg);
        }
    }

    /**
     * 断开连接。
     * 同步锁锁定此类
     */
    public static void disConnect() {
        if (null != impinjReader && impinjReader.isConnected()) {
            synchronized (ImpinjReaderSingleton.class) {
                impinjReader.disconnect();
                log.info("Impinj Reader hostname:" + impinjReader.getAddress() + " name: "+ impinjReader.getName() +"断开连接！！");
                return;
            }
        }
        log.info("Impinj Reader hostname:" + impinjReader.getAddress() + " name: "+ impinjReader.getName() +"已断开连接！！");
    }


}
