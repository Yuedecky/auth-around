package com.broad.security.auth.core.properties;

import com.broad.security.auth.core.validate.generator.ImageCodeGenerator;
import com.broad.security.auth.core.validate.generator.ValidateCodeGenerator;
import com.broad.security.auth.core.validate.provider.DefaultSmsSenderProviderImpl;
import com.broad.security.auth.core.validate.provider.SmsCodeSenderProvider;
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
    public ValidateCodeGenerator imageCodeGenerator() {
        ValidateCodeGenerator producer = new ImageCodeGenerator();
        ((ImageCodeGenerator) producer).setCoreProperties(coreProperties);
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
