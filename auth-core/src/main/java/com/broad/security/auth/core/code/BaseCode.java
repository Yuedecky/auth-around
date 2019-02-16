package com.broad.security.auth.core.code;

import java.time.LocalDateTime;

public abstract class BaseCode {
    private String code;

    private LocalDateTime expireTime;

    public BaseCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expireTime);
    }

}
