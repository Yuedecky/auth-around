package com.broad.security.auth.core.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {CoreProperties.class})
public class CoreSecurityConfiguration {
}
