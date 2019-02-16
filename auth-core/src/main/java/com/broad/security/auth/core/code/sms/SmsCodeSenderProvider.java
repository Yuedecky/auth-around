package com.broad.security.auth.core.code.sms;

public interface SmsCodeSenderProvider {

    void sendSms(String mobile,String code);
}
