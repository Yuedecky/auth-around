package com.broad.security.auth.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "auth.security")
@Configuration
public class CoreProperties {

    private BrowserProperties browserProperties = new BrowserProperties();

    public BrowserProperties getBrowserProperties() {
        return browserProperties;
    }

    public void setBrowserProperties(BrowserProperties browserProperties) {
        this.browserProperties = browserProperties;
    }
}
