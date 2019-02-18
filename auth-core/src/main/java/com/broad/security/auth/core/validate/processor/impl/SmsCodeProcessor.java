package com.broad.security.auth.core.validate.processor.impl;

import com.broad.security.auth.core.properties.constants.AuthConstants;
import com.broad.security.auth.core.validate.code.BaseCode;
import com.broad.security.auth.core.validate.processor.AbstractValidateCodeProcessor;
import com.broad.security.auth.core.validate.provider.SmsCodeSenderProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;


@Component
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<BaseCode> {

    @Autowired
    private SmsCodeSenderProvider defaultSmsSenderProviderImpl;

    @Override
    public void send(ServletWebRequest request, BaseCode code) throws ServletRequestBindingException {
        String parameterName = AuthConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        defaultSmsSenderProviderImpl.sendSms(ServletRequestUtils.getStringParameter(request.getRequest(), parameterName),
                code.getCode());
    }
}
