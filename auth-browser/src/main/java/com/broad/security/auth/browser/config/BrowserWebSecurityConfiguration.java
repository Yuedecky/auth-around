package com.broad.security.auth.browser.config;

import com.broad.security.auth.browser.config.handler.LoginFailureHandler;
import com.broad.security.auth.browser.config.handler.LoginSuccessHandler;
import com.broad.security.auth.core.config.properties.CoreProperties;
import com.broad.security.auth.core.config.properties.IgnoreUrlProperties;
import com.broad.security.auth.core.web.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@Order(1)
public class BrowserWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CoreProperties coreProperties;

    @Autowired
    private LoginSuccessHandler successHandler;

    @Value("${auth.security.browser.default-login-page:/logins.html}")
    private String defaultLoginPage;

    @Autowired
    private IgnoreUrlProperties ignoreUrlProperties;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private LoginFailureHandler loginFailureHandler;


    @Autowired
    private ValidateCodeFilter validateCodeFilter;


    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> ignoreUrls = ignoreUrlProperties.getIgnoreUrls();
        ignoreUrls.add(defaultLoginPage);
        ignoreUrls.add(coreProperties.getBrowser().getLoginPage());
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint).and().formLogin().failureHandler(loginFailureHandler)
                .successHandler(successHandler)
                .loginProcessingUrl("/authentication/form").and().logout().logoutUrl("/authentication/logout")
                .and().rememberMe().tokenRepository(tokenRepository())
                .tokenValiditySeconds(coreProperties.getBrowser().getRememberMeSeconds()).userDetailsService(userDetailsService()).and()
                .authorizeRequests().antMatchers(ignoreUrls.toArray(new String[ignoreUrls.size()])).permitAll().anyRequest().authenticated()
                .and().csrf().disable();
    }

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
