package com.broad.security.auth.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@ConfigurationProperties(prefix = "auth.security")
@Component
@Valid
public class CoreProperties {

    @Valid
    private BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser){
        this.browser = browser;
    }



}
