package com.broad.security.auth.core.web;

import lombok.Getter;

@Getter
public enum CommonResponseCode {

    RC_EXCEPTION("service exception"),
    RC_SUCCESS("success"),
    RC_ERROR("error"),
    ;
    private String code;

    CommonResponseCode(String code) {
        this.code = code;
    }
}
