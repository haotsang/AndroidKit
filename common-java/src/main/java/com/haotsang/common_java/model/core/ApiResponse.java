package com.haotsang.common_java.model.core;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {

    @SerializedName(
            value = "code",
            alternate = {"retCode"}
    )
    private int code;
    @SerializedName(
            value = "message",
            alternate = {"msg", "reason"}
    )
    private String message;
    @SerializedName(
            value = "result",
            alternate = {"data"}
    )
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


    public boolean isSuccess() {
        return code == 200 && result != null;
    }
}
