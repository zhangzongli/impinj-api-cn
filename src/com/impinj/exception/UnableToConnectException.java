package com.impinj.exception;

/**
 * 无法连接异常
 */
public class UnableToConnectException extends Throwable {

    private String msg;

    public UnableToConnectException(String msg) {
        this.msg = msg;
    }
    public String getMessage() {
        return msg;
    }

}
