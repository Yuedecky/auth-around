package com.broad.security.auth.core.config.properties;

import java.util.Set;

public class SmsCodeProperties {

    private int length;

    private int expireIn;

    private Set<String> urls;

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
