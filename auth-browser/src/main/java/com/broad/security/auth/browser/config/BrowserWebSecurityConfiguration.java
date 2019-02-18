package com.broad.security.auth.browser.config;

import com.broad.security.auth.browser.config.handler.AuthFailureHandler;
import com.broad.security.auth.browser.config.handler.AuthSuccessHandler;
import com.broad.security.auth.core.mobile.SmsCodeAuthenticationSecurityConfiguration;
import com.broad.security.auth.core.properties.CoreProperties;
import com.broad.security.auth.core.properties.IgnoreUrlProperties;
import com.broad.security.auth.core.validate.ValidateCodeSecurityConfig;
import com.broad.security.auth.core.web.AbstractChannelSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;

@Configuration
@Order(1)
public class BrowserWebSecurityConfiguration extends AbstractChannelSecurityConfig {

    @Autowired
    private CoreProperties coreProperties;

    @Autowired
    private AuthSuccessHandler successHandler;

    @Value("${auth.security.browser.default-login-page:/logins.html}")
    private String defaultLoginPage;


    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private AuthFailureHandler loginFailureHandler;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private SmsCodeAuthenticationSecurityConfiguration smsCodeAuthenticationSecurityConfiguration;


    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        applyPasswordAuthenticationConfig(http);
        http.apply(validateCodeSecurityConfig).and().apply(smsCodeAuthenticationSecurityConfiguration).and()
                .rememberMe().tokenRepository(tokenRepository())
                .tokenValiditySeconds(coreProperties.getBrowser().getRememberMeSeconds()).userDetailsService(userDetailsService())
                .and().sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(coreProperties.getBrowser().getSession().getMaxSessions())
                .maxSessionsPreventsLogin(coreProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                .and().and().authorizeRequests();


    }

    /**
     * 记住我需要存储
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Bean
    public MappingJackson2HttpMessageConverter messageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }


}
