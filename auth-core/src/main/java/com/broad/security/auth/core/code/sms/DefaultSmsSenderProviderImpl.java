package com.broad.security.auth.core.code.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("defaultSmsSenderProviderImpl")
public class DefaultSmsSenderProviderImpl implements SmsCodeSenderProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSmsSenderProviderImpl.class);

    @Override
    public void sendSms(String mobile, String code) {
        LOGGER.info("向手机号{}发送了短信：{}",mobile,code);
    }
}
