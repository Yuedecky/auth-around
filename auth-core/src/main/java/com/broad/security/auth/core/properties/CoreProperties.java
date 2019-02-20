package com.broad.security.auth.core.properties;

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

    @Valid
    private SocialProperties social = new SocialProperties();

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }

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
