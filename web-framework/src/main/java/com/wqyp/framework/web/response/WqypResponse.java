package com.wqyp.framework.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WqypResponse<T> implements Serializable {

    private static final long serialVersionUID = 235659834L;
    @Valid
    private T data;
    private int code;
    private String msg;

    public WqypResponse(T data) {
        this.data = data;
    }


    public WqypResponse(String msg, int code) {
        this(null, code, msg);
    }

    public static <T> WqypResponse<T> success(T data) {
        return new WqypResponse<>(data, WqypResponseCode.RC_SUCCESS.getCode(), WqypResponseCode.RC_SUCCESS.getMsg());
    }



    public static <T> WqypResponse<T> error(String message) {
        return new WqypResponse<>(message, WqypResponseCode.RC_ERROR.getCode());
    }

}
