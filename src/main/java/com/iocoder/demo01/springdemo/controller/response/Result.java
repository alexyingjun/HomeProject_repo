package com.iocoder.demo01.springdemo.controller.response;

import lombok.Getter;

public class Result {
    @Getter private int code;
    @Getter private String message;
    @Getter private Object data;
    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }
}
