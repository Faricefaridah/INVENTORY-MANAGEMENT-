package com.ims.app.Config;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String message;
    private T data;
    private int statusCode;

    public ApiResponse(String message, T data, int statusCode) {
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
    }
}

