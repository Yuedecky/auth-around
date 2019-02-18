package com.broad.security.auth.core.validate.code;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SmsCode extends BaseCode{

    public SmsCode(String code, LocalDateTime expireTime) {
        super(code, expireTime);
    }
}
