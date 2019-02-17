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



    @Valid
    private CodeProperties code = new CodeProperties();

    public CodeProperties getCode() {
        return code;
    }

    public void setCode(CodeProperties code) {
        this.code = code;
    }

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }



}
