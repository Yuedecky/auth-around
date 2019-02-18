package com.broad.security.auth.core.web.strategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

@Configuration
public class SessionStrategyConfiguration {

    @Value("${auth.security.session.redirect-url}")
    private String sessionRedirectUrl;

    @Bean
    public InvalidSessionStrategy sessionStrategy() {
        return new InvalidateSessionStrategyBean(sessionRedirectUrl);
    }

    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new SessionInformationExpiredStrategyBean();
    }
}
