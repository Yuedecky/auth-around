package com.broad.security.auth.core.config;

import com.broad.security.auth.core.code.ImageCodeGenerator;
import com.broad.security.auth.core.code.ValidateCodeProducer;
import com.broad.security.auth.core.config.properties.CoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfiguration {

    @Autowired
    private CoreProperties coreProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeProducer codeProducer() {
        ValidateCodeProducer producer = new ImageCodeGenerator();
        producer.setCoreProperties(coreProperties);
        return producer;
    }
}
