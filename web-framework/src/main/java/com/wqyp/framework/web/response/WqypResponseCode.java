package com.wqyp.framework.web.response;

public enum WqypResponseCode {

    RC_SUCCESS(1,"请求成功"),
    RC_EXCEPTION(2,"内部异常"),
    RC_ERROR(3,"请求错误"),
    ;

    private int code;
    private String msg;

    WqypResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
