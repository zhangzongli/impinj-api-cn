package com.impinj.model;

import com.impinj.enums.InOrOutEnum;

import java.util.List;

/**
 * @program: impinj-api-cn
 * @description: 标签信息model
 * @author: zhangzl
 * @create: 2020-06-08 21:43
 **/
public class TagDataModel {

    /** 标签id */
    private String tid;

    /** 用户自定义内存 */
    private String userMemory;

    /** epc */
    private String epc;

    /** 读取天线端口号顺序 */
    private List<String> antennaPortList;

    /** 判断第一个被读取到的天线端口号 是出库还是入库 */
    private InOrOutEnum firstInOrOutEnum;

    /** 判断第二个被读取到的天线端口号 是出库还是入库 */
    private InOrOutEnum secondInOrOutEnum;

    /** 查询db 判断出库还是入库 */
    private InOrOutEnum dbInOrOutEnum;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getUserMemory() {
        return userMemory;
    }

    public void setUserMemory(String userMemory) {
        this.userMemory = userMemory;
    }

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public List<String> getAntennaPortList() {
        return antennaPortList;
    }

    public void setAntennaPortList(List<String> antennaPortList) {
        this.antennaPortList = antennaPortList;
    }

    public InOrOutEnum getFirstInOrOutEnum() {
        return firstInOrOutEnum;
    }

    public void setFirstInOrOutEnum(InOrOutEnum firstInOrOutEnum) {
        this.firstInOrOutEnum = firstInOrOutEnum;
    }

    public InOrOutEnum getSecondInOrOutEnum() {
        return secondInOrOutEnum;
    }

    public void setSecondInOrOutEnum(InOrOutEnum secondInOrOutEnum) {
        this.secondInOrOutEnum = secondInOrOutEnum;
    }

    public InOrOutEnum getDbInOrOutEnum() {
        return dbInOrOutEnum;
    }

    public void setDbInOrOutEnum(InOrOutEnum dbInOrOutEnum) {
        this.dbInOrOutEnum = dbInOrOutEnum;
    }
}
