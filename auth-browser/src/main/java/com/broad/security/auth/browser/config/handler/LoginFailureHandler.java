package com.broad.security.auth.browser.config.handler;

import com.alibaba.fastjson.JSON;
import com.broad.security.auth.core.config.enums.LoginType;
import com.broad.security.auth.core.config.properties.CoreProperties;
import com.broad.security.auth.core.web.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CoreProperties coreProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        log.error("login failure,error:{}", exception.getMessage());
        if (coreProperties.getBrowser().getLoginType().equals(LoginType.JSON)) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(new Response(objectMapper.writeValueAsString(exception.getMessage())).toString());
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
