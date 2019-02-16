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
    private SmsCodeProperties smsCodeProperties = new SmsCodeProperties();

    @Valid
    private CodeProperties codeProperties = new CodeProperties();

    public CodeProperties getCodeProperties() {
        return codeProperties;
    }

    public void setCodeProperties(CodeProperties codeProperties) {
        this.codeProperties = codeProperties;
    }

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }


    public SmsCodeProperties getSmsCodeProperties() {
        return smsCodeProperties;
    }

    public void setSmsCodeProperties(SmsCodeProperties smsCodeProperties) {
        this.smsCodeProperties = smsCodeProperties;
    }
}
