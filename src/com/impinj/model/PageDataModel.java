package com.impinj.model;

import com.impinj.enums.InOrOutEnum;

import java.util.Date;

/**
 * @program: impinj-api-cn
 * @description: 页面展示model
 * @author: zhangzl
 * @create: 2020-06-11 21:01
 **/
public class PageDataModel {

    /** 标签id */
    private String tid;

    /** 用户自定义内存 */
    private String userMemory;

    /** epc */
    private String epc;

    /** 查询db 判断出库还是入库 */
    private InOrOutEnum dbInOrOutEnum;

    /** 操作时间 */
    private Date operatingTime;

    /** 设备名称 */
    private String name;

    /** 一级分类 */
    private String firstType;

    /** 二级分类 */
    private String secondType;

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

    public InOrOutEnum getDbInOrOutEnum() {
        return dbInOrOutEnum;
    }

    public void setDbInOrOutEnum(InOrOutEnum dbInOrOutEnum) {
        this.dbInOrOutEnum = dbInOrOutEnum;
    }

    public Date getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(Date operatingTime) {
        this.operatingTime = operatingTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstType() {
        return firstType;
    }

    public void setFirstType(String firstType) {
        this.firstType = firstType;
    }

    public String getSecondType() {
        return secondType;
    }

    public void setSecondType(String secondType) {
        this.secondType = secondType;
    }
}
