package com.broad.security.spring.roadmap.config;

import com.broad.security.spring.roadmap.config.customer.CustomAuthenticationEntryPoint;
import com.broad.security.spring.roadmap.config.customer.CustomerLogoutSuccessHandler;
import com.broad.security.spring.roadmap.config.customer.IgnoreUrlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    @Autowired
    private CustomerLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
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
