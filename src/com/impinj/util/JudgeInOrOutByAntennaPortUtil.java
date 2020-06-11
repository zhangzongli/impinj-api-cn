package com.impinj.util;

import com.impinj.enums.InOrOutEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: impinj-api-cn
 * @description: 根据端口号判断 出入库
 * @author: zhangzl
 * @create: 2020-06-08 22:43
 **/
public class JudgeInOrOutByAntennaPortUtil {

    // 出入库判断逻辑
    // 12 天线在门外  34 天线在门内
    // 设备出库，必定先被 34 天线扫描到，再被12天线扫描到。
    // 设备入库，必定先被12 天线扫描到，再被34天线扫描到。
    // 判断过程：
    //   出库状态
    //   被34天线扫描到，是预出库状态，此时不提示不播报出库成功，直到被12天线扫描到，则提示出库成功。
    //   入库状态
    //   被12天线扫描到，是预入库状态(直接判断为入库也没有问题，考虑极端情况，有人中途不想归还了。)，直到被34天线扫描到，则提示入库成功，
    // 极端情况：
    //   出入库时，在反复横跳，出来进去出来进去。只看第一次和最后一次去判断出入库

    /** 入库端口数据 */
    private static final List<String> inPortList = new ArrayList<String>() {
        {
            add("1");
            add("2");
        }
    };

    /** 出库端口数据 */
    private static final List<String> outPortList = new ArrayList<String>() {
        {
            add("3");
            add("4");
        }
    };

    /**
     * 通过第一次读取到的天线端口号
     * @param port
     * @return
     */
    public static InOrOutEnum judgeInOrOurByFirstAntennaPort(String port) {
        if (inPortList.contains(port)) {
            return InOrOutEnum.IN;
        }else if (outPortList.contains(port)) {
            return InOrOutEnum.OUT;
        }
        return null;
    }
}
