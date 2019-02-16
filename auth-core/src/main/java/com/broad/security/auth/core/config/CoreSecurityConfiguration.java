package com.broad.security.auth.core.config;

import com.broad.security.auth.core.config.properties.CoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {CoreProperties.class})
public class CoreSecurityConfiguration {
}
