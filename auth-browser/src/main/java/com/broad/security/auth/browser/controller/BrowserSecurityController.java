package com.broad.security.auth.browser.controller;

import com.broad.security.auth.core.config.properties.CoreProperties;
import com.broad.security.auth.core.web.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/authentication")
public class BrowserSecurityController {
    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Resource
    private CoreProperties coreProperties;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response<String> login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            log.info("求情跳转url:{}", redirectUrl);
            if (redirectUrl.endsWith(".html")) {
                redirectStrategy.sendRedirect(request, response, coreProperties.getBrowserProperties().getLoginPage());
            }
        }
        return Response.error("access denied", HttpStatus.UNAUTHORIZED.value());
    }
}
