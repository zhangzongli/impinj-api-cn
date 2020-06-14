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
     * 通过被读取到的天线端口号判断，标签是在库房外还是在库房内
     * 此处的InOrOutEnum 不是出入库的概念。是标签被库房内的天线读取到还是被库房外的天线读取到
     * @param port 端口号
     * @return
     */
    public static String judgeInOrOurOfHouseByFirstAntennaPort(String port) {
        if (inPortList.contains(port)) {
            return InOrOutEnum.IN.getCode();
        }else if (outPortList.contains(port)) {
            return InOrOutEnum.OUT.getCode();
        }
        return null;
    }

    /**
     * 根据室内外天线读取顺序列表，判断该设备的出入库状态
     * @param antennatList
     * @return
     */
    public static InOrOutEnum judgeInOrOurByAntennaList(List<String> antennatList) {
        // 只被一端的天线读取到，出入库状态未知。
        if (antennatList.size() == 1) {
            return InOrOutEnum.NONE;
        }

        InOrOutEnum firstStatus = InOrOutEnum.getInOrOutEnum(antennatList.get(0));
        InOrOutEnum lastStatus = InOrOutEnum.getInOrOutEnum(antennatList.get(antennatList.size() - 1));

        if (firstStatus.equals(lastStatus)) {
            // 最先被读取的天线与最后被读取的天线位于库房的同一端，出入库状态未知。
            return InOrOutEnum.NONE;
        }

        if (InOrOutEnum.IN.equals(lastStatus)) {
            // 若最后被读取的天线位于库房内一端，则为入库
            return InOrOutEnum.IN;
        }

        if (InOrOutEnum.OUT.equals(lastStatus)) {
            // 若最后被读取的天线位于库房外一端，则为出库
            return InOrOutEnum.OUT;
        }

        // 其他为none
        return InOrOutEnum.NONE;
    }
}
