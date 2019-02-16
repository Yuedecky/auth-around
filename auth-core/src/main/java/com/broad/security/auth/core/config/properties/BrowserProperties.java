package com.broad.security.auth.core.config.properties;


import com.broad.security.auth.core.config.enums.LoginType;

public class BrowserProperties {

    private String loginPage;

    private LoginType loginType = LoginType.JSON;

    private String redirectUrl;

    private int rememberMeSeconds;

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getLoginPage() {
        return loginPage;
    }


    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }


}
