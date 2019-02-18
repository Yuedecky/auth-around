package com.broad.security.auth.core.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IgnoreUrlProperties {

    @Value("${authentication.oauth.ignoreUrls}")
    private List<String> ignoreUrls;

    @Value("${server.servlet.context-path}")
    private String prefix;

    public List<String> getIgnoreUrls() {
        return ignoreUrls;
    }

    public void setIgnoreUrls(List<String> ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}