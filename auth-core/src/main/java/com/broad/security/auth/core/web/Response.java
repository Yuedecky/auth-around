package com.broad.security.auth.core.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements Serializable {

    private T data;

    private int error = 0;

    private String responseCode;

    public Response(T data) {
        this.data = data;
    }

    public Response(T data, String code, int error) {
        this.data = data;
        this.error = error;
        this.responseCode = code;
    }

    public Response(String responseCode, int error) {
        this(null, responseCode, error);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data, CommonResponseCode.RC_SUCCESS.getCode(), HttpStatus.OK.value());
    }

    public static <T> Response<T> error(T data) {
        return new Response<>(data, CommonResponseCode.RC_ERROR.getCode(), 1);
    }

    public static <T> Response<T> error(String message) {
        return new Response<>(message, 1);
    }

    public static Response error(Object data, String responseMessage) {
        return new Response<>(data, responseMessage, 1);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
