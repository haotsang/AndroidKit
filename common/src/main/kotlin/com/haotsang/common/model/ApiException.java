package com.haotsang.common.model;

public class ApiException extends RuntimeException {

    private final int statusCode;
    private final String errorMessage;

    public ApiException(int statusCode, String errorMessage) {
        super("HTTP Status Code: " + statusCode + ", Error Message: " + errorMessage);
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static ApiException error(int statusCode, String errorMessage) {
        return new ApiException(statusCode, errorMessage);
    }
}