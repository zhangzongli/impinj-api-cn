package com.impinj.test;

import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;

import java.util.Arrays;

public class IsConnection {

    public static void main(String[] args) throws OctaneSdkException {
        ImpinjReader impinjReader = new ImpinjReader();

        System.out.println("暂未连接，查看连接状态:" +  impinjReader.isConnected());
        impinjReader.connect("169.254.1.1");
        System.out.println("已连接，查看连接状态:" +  impinjReader.isConnected());

        impinjReader.disconnect();
        System.out.println("断开连接");





    }
}
