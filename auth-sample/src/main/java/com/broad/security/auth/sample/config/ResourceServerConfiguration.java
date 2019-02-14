package com.broad.security.auth.sample.config;

import com.broad.security.auth.sample.config.customer.CustomAuthenticationEntryPoint;
import com.broad.security.auth.sample.config.customer.CustomerLogoutSuccessHandler;
import com.broad.security.auth.sample.config.customer.IgnoreUrlProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;


@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    @Resource
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Resource
    private CustomerLogoutSuccessHandler logoutSuccessHandler;

    @Resource
    private IgnoreUrlProperties ignoreUrlProperties;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .logout()
                .logoutUrl("/oauth/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                .disable()
                .headers()
                .frameOptions().disable();
        String[] ignoreUrls = ignoreUrlProperties.getIgnoreUrls();
        http.authorizeRequests().antMatchers(ignoreUrls).permitAll().anyRequest().authenticated();
    }


}
