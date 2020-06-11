package com.impinj.enums;

/**
 * @program: impinj-api-cn
 * @description: 出入库枚举类
 * @author: zhangzl
 * @create: 2020-06-08 22:07
 **/
public enum InOrOutEnum {

    NONE("none", "未操作"),
    IN("in", "入库"),
    OUT("out", "出库");

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    InOrOutEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static InOrOutEnum getInOrOutEnum(String code) {
        for (InOrOutEnum value : InOrOutEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
