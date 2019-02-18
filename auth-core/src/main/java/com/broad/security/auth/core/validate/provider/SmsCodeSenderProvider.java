package com.broad.security.auth.core.validate.provider;

public interface SmsCodeSenderProvider {

    void sendSms(String mobile,String code);
}
