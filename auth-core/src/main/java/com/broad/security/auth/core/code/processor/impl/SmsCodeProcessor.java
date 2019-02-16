package com.broad.security.auth.core.code.processor.impl;

import com.broad.security.auth.core.code.SmsCode;
import com.broad.security.auth.core.code.processor.AbstractValidateCodeProcessor;
import com.broad.security.auth.core.code.sms.SmsCodeSenderProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;


@Component
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<SmsCode> {

    @Autowired
    private SmsCodeSenderProvider defaultSmsSenderProviderImpl;

    @Override
    protected void send(ServletWebRequest request, SmsCode code) throws ServletRequestBindingException {
        defaultSmsSenderProviderImpl.sendSms(ServletRequestUtils.getStringParameter(request.getRequest(), "mobile"),
                code.getCode());
    }
}
