package com.broad.security.auth.core.web.filter;

import com.broad.security.auth.core.code.ImageCode;
import com.broad.security.auth.core.config.properties.CoreProperties;
import com.broad.security.auth.core.web.ValidateCodeController;
import com.broad.security.auth.core.web.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private final AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private CoreProperties coreProperties;

    private List<String> urlsToFilter;

    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        urlsToFilter = coreProperties.getCodeProperties().getImage().getUrls();
        if(urlsToFilter == null){
            urlsToFilter = new ArrayList<>();
        }
        urlsToFilter.add("/authentication/form");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean filter = false;
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String url : urlsToFilter) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                filter = true;
            }
        }
        if (filter) {
            try {
                validateCode(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(ServletWebRequest webRequest) throws ServletRequestBindingException {
        ImageCode code = (ImageCode) sessionStrategy.getAttribute(webRequest, ValidateCodeController.SESSION_KEY);
        String codeInRequest = ServletRequestUtils.getStringParameter(webRequest.getRequest(), "image");
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("image code cannot be null");
        }
        if (code == null) {
            throw new ValidateCodeException("image code not exists");
        }
        if (code.isExpired()) {
            sessionStrategy.removeAttribute(webRequest, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("image code has expired");
        }
        if (!StringUtils.equalsIgnoreCase(code.getCode(), codeInRequest)) {
            throw new ValidateCodeException("image code not matches");
        }
        sessionStrategy.removeAttribute(webRequest, ValidateCodeController.SESSION_KEY);
    }
}
