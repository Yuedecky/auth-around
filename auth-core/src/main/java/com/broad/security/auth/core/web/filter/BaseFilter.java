package com.broad.security.auth.core.web.filter;

import com.broad.security.auth.core.code.BaseCode;
import com.broad.security.auth.core.config.properties.CoreProperties;
import com.broad.security.auth.core.web.exception.ValidateCodeException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

public abstract class BaseFilter extends OncePerRequestFilter implements InitializingBean {


    private CoreProperties coreProperties;

    private AuthenticationFailureHandler authenticationFailureHandler;

    private Collection<String> urlsIgnored;

    private SessionStrategy httpSessionStrategy;

    public CoreProperties getCoreProperties() {
        return coreProperties;
    }

    public SessionStrategy getHttpSessionStrategy() {
        return httpSessionStrategy;
    }

    protected BaseCode getAttribute(ServletWebRequest webRequest, String attributeKey) {
        return (BaseCode) this.httpSessionStrategy.getAttribute(webRequest, attributeKey);
    }


    private PathMatcher pathMatcher = new AntPathMatcher();

    public void setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    protected void removeAttribute(ServletWebRequest request, String attributeKey) {
        this.httpSessionStrategy.removeAttribute(request, attributeKey);
    }

    @Autowired
    public BaseFilter(CoreProperties coreProperties, AuthenticationFailureHandler authenticationFailureHandler, SessionStrategy sessionStrategy) {
        this.coreProperties = coreProperties;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.httpSessionStrategy = sessionStrategy;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean filter = false;
        for (String url : urlsIgnored) {
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

    protected abstract void validateCode(ServletWebRequest servletWebRequest) throws ServletRequestBindingException;


    public void setUrlsIgnored(Collection<String> urlsIgnored) {
        this.urlsIgnored = urlsIgnored;
    }


}
