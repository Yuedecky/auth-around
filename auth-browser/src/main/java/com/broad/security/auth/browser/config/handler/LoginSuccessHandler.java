package com.broad.security.auth.browser.config.handler;

import com.broad.security.auth.core.config.enums.LoginType;
import com.broad.security.auth.core.config.properties.CoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private CoreProperties coreProperties;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("login success");
        if (coreProperties.getBrowser().getLoginType() == LoginType.JSON) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(new String(objectMapper.writeValueAsBytes(authentication)));
        } else {
            response.sendRedirect(webApplicationContext.getServletContext().getContextPath() + coreProperties.getBrowser().getRedirectUrl());
        }
    }
}
