package com.broad.security.auth.core.code.generator;

import com.broad.security.auth.core.code.BaseCode;
import com.broad.security.auth.core.code.SmsCode;
import com.broad.security.auth.core.config.properties.CoreProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;

@Component(value = "smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeRule {


    private CoreProperties coreProperties;

    @Override
    public BaseCode createRule(ServletWebRequest servletWebRequest) {
        String code = RandomStringUtils.randomNumeric(coreProperties.getSmsCodeProperties().getLength());
        return new SmsCode(code, LocalDateTime.now().plusSeconds(coreProperties.getSmsCodeProperties().getExpireIn()));
    }

    @Override
    public void setCoreProperties(CoreProperties coreProperties) {
        this.coreProperties = coreProperties;
    }
}
