package com.broad.security.auth.core.web.filter;

import com.broad.security.auth.core.code.BaseCode;
import com.broad.security.auth.core.code.processor.ValidateCodeProcessor;
import com.broad.security.auth.core.config.properties.CoreProperties;
import com.broad.security.auth.core.web.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;

@Component
public class SmsCodeFilter extends BaseFilter {


    @Autowired
    public SmsCodeFilter(AuthenticationFailureHandler authenticationFailureHandler, CoreProperties coreProperties, SessionStrategy httpSessionStrategy) {
        super(coreProperties, authenticationFailureHandler, httpSessionStrategy);
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        Assert.notNull(getCoreProperties().getCode().getSms().getUrls(),"要忽略的url不能为空");
        setUrlsIgnored(getCoreProperties().getCode().getSms().getUrls());
    }

    @Override
    public void validateCode(ServletWebRequest webRequest) throws ServletRequestBindingException {
        BaseCode code = getAttribute(webRequest, ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
        String codeInRequest = ServletRequestUtils.getStringParameter(webRequest.getRequest(), "smsCode");
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("sms code cannot be null");
        }
        if (code == null) {
            throw new ValidateCodeException("sms code not exists");
        }
        if (code.isExpired()) {
            removeAttribute(webRequest, ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
            throw new ValidateCodeException("sms code has expired");
        }
        if (!StringUtils.equalsIgnoreCase(code.getCode(), codeInRequest)) {
            throw new ValidateCodeException("sms code not matches");
        }
        removeAttribute(webRequest, ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
    }
}
