package com.haotsang.common.model;

public class ErrorData {

    private int errorCode;
    private String errorMessage;

    public ErrorData() {
    }
    public ErrorData(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static ErrorData parseException(Throwable ex) {
        return new ErrorData(-1, ex.getMessage());
    }
}
