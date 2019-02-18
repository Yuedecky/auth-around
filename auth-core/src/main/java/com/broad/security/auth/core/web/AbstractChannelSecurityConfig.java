package com.broad.security.auth.core.web;

import com.broad.security.auth.core.properties.constants.AuthConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.formLogin().loginPage(AuthConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM).successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler).loginProcessingUrl(AuthConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM);
    }

}
