package com.broad.security.auth.browser.config;

import com.broad.security.auth.core.config.properties.CoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Autowired
    private CoreProperties coreProperties;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        LOGGER.error("pre-authenticated entry point called ,error:{}",authException.getMessage());
        LOGGER.info("requestUrl:{},queryString:{}", request.getRequestURL(), request.getQueryString());
        response.sendRedirect(contextPath + coreProperties.getBrowser().getLoginPage());
    }
}
