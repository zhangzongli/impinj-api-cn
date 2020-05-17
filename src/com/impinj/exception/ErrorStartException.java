package com.impinj.exception;

/**
 * 无法连接异常
 */
public class ErrorStartException extends Throwable {

    private String msg;

    public ErrorStartException(String msg) {
        this.msg = msg;
    }
    public String getMessage() {
        return msg;
    }

}
