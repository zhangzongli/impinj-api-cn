package com.impinj.exception;

/**
 * 无法连接异常
 */
public class ErrorStopException extends Throwable {

    private String msg;

    public ErrorStopException(String msg) {
        this.msg = msg;
    }
    public String getMessage() {
        return msg;
    }

}
