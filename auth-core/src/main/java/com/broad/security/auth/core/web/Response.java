package com.broad.security.auth.core.web;

import lombok.Data;

@Data
public class Response<T> {

    private T data;

    private int code;

    public Response(T data) {
        this.data = data;
    }

    public Response(T data, int code) {
        this.data = data;
        this.code = code;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data);
    }

    public static <T> Response<T> error(T data, int code) {
        return new Response<>(data, code);
    }
}
