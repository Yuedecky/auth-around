package com.broad.security.auth.browser.config.handler;

import com.broad.security.auth.core.properties.enums.LoginType;
import com.broad.security.auth.core.properties.CoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private CoreProperties coreProperties;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException {
        log.info("auth success");
        if (coreProperties.getBrowser().getLoginType().equals(LoginType.JSON)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(new String(objectMapper.writeValueAsBytes(authentication)));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);;
        }
    }
}
