package com.broad.security.auth.core.validate;

import com.broad.security.auth.core.properties.CoreProperties;
import com.broad.security.auth.core.validate.generator.ImageCodeGenerator;
import com.broad.security.auth.core.validate.generator.SmsCodeGenerator;
import com.broad.security.auth.core.validate.generator.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseCodeBeanConfiguration {

    @Autowired
    private CoreProperties coreProperties;

    @Bean(value = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setCoreProperties(coreProperties);
        return imageCodeGenerator;
    }


    @Bean(value = "smsCodeGenerator")
    public ValidateCodeGenerator smsCodeGenerator() {
        SmsCodeGenerator smsCodeGenerator = new SmsCodeGenerator();
        smsCodeGenerator.setCoreProperties(coreProperties);
        return smsCodeGenerator;
    }


}
