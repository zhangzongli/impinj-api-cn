package com.impinj.model;

import com.impinj.enums.InOrOutEnum;

import java.util.Date;
import java.util.List;

/**
 * @program: impinj-api-cn
 * @description: 标签信息model
 * @author: zhangzl
 * @create: 2020-06-08 21:43
 **/
public class TagDataModel {

    /** 读取时间 */
    private Date readTime;

    /** 标签id */
    private String tid;

    /** 用户自定义内存 */
    private String userMemory;

    /** epc */
    private String epc;

    /**
     * 读取天线端口号顺序
     * in : 代表被屋内天线扫描到
     * out : 代表被屋外天线扫描到
     */
    private List<String> inOrOuthousetList;

    /** 通过天线判断 是出库还是入库 */
    private InOrOutEnum inOrOut;

    /** 查询db 判断出库还是入库 */
    private InOrOutEnum dbInOrOutEnum;

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

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

    public List<String> getInOrOuthousetList() {
        return inOrOuthousetList;
    }

    public void setInOrOuthousetList(List<String> inOrOuthousetList) {
        this.inOrOuthousetList = inOrOuthousetList;
    }

    public InOrOutEnum getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(InOrOutEnum inOrOut) {
        this.inOrOut = inOrOut;
    }

    public InOrOutEnum getDbInOrOutEnum() {
        return dbInOrOutEnum;
    }

    public void setDbInOrOutEnum(InOrOutEnum dbInOrOutEnum) {
        this.dbInOrOutEnum = dbInOrOutEnum;
    }
}
