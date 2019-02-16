package com.broad.security.auth.core.config;

import com.broad.security.auth.core.code.generator.ImageCodeGenerator;
import com.broad.security.auth.core.code.generator.ValidateCodeRule;
import com.broad.security.auth.core.code.sms.DefaultSmsSenderProviderImpl;
import com.broad.security.auth.core.code.sms.SmsCodeSenderProvider;
import com.broad.security.auth.core.config.properties.CoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;

@Configuration
public class ValidateCodeBeanConfiguration {

    @Autowired
    private CoreProperties coreProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeRule imageCodeGenerator() {
        ValidateCodeRule producer = new ImageCodeGenerator();
        producer.setCoreProperties(coreProperties);
        return producer;
    }


    @Bean
    @ConditionalOnMissingBean(name = "smsCodeSenderProvider")
    public SmsCodeSenderProvider smsCodeSenderProvider() {
        return new DefaultSmsSenderProviderImpl();
    }

    @Bean(value = "sessionStrategy")
    public SessionStrategy sessionStrategy() {
        return new HttpSessionSessionStrategy();
    }
}
