package com.broad.security.auth.core.web;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
public class Response implements Serializable {

    private Object data;

    private int code;

    public Response(Object data) {
        this.data = data;
    }

    public Response(Object data, int code) {
        this.data = data;
        this.code = code;
    }

    public static Response success(Object data) {
        return new Response(data, HttpStatus.OK.value());
    }

    public static Response error(Object data, int code) {
        return new Response(data, code);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
