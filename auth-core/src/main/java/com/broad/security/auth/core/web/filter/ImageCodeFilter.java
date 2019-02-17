package com.broad.security.auth.core.web.filter;

import com.broad.security.auth.core.code.ImageCode;
import com.broad.security.auth.core.code.processor.ValidateCodeProcessor;
import com.broad.security.auth.core.config.properties.CoreProperties;
import com.broad.security.auth.core.web.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;


@Component
public class ImageCodeFilter extends BaseFilter {


    public ImageCodeFilter(AuthenticationFailureHandler authenticationFailureHandler, CoreProperties coreProperties, SessionStrategy sessionStrategy) {
        super(coreProperties,authenticationFailureHandler,sessionStrategy);
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        Assert.notNull(getCoreProperties().getCode().getImage().getUrls(),"要忽略的urls不能为空");
        setUrlsIgnored(getCoreProperties().getCode().getImage().getUrls());
    }

    @Override
    public void validateCode(ServletWebRequest webRequest) throws ServletRequestBindingException {
        ImageCode code = (ImageCode) super.getHttpSessionStrategy().getAttribute(webRequest, ValidateCodeProcessor.SESSION_KEY_PREFIX + "IMAGE");
        String codeInRequest = ServletRequestUtils.getStringParameter(webRequest.getRequest(), "imageCode");
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("image code cannot be null");
        }
        if (code == null) {
            throw new ValidateCodeException("image code not exists");
        }
        if (code.isExpired()) {
            removeAttribute(webRequest, ValidateCodeProcessor.SESSION_KEY_PREFIX + "IMAGE");
            throw new ValidateCodeException("image code has expired");
        }
        if (!StringUtils.equalsIgnoreCase(code.getCode(), codeInRequest)) {
            throw new ValidateCodeException("image code not matches");
        }
        removeAttribute(webRequest, ValidateCodeProcessor.SESSION_KEY_PREFIX + "IMAGE");
    }
}
