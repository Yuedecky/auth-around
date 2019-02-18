package com.broad.security.auth.core.validate.generator;

import com.broad.security.auth.core.validate.code.BaseCode;
import com.broad.security.auth.core.validate.code.SmsCode;
import com.broad.security.auth.core.properties.CoreProperties;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;

@Data
public class SmsCodeGenerator implements ValidateCodeGenerator {


    private CoreProperties coreProperties;


    @Override
    public BaseCode generate(ServletWebRequest servletWebRequest) {
        checkProperties(coreProperties);
        String code = RandomStringUtils.randomNumeric(coreProperties.getCode().getSms().getLength());
        return new SmsCode(code, LocalDateTime.now().plusSeconds(coreProperties.getCode().getSms().getExpireIn()));
    }


}
