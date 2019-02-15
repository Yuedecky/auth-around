package com.broad.security.auth.browser.config;

import com.broad.security.auth.core.config.properties.CoreProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
public class BrowserWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private CoreProperties coreProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/authentication/login").loginProcessingUrl("/authentication/form").and()
                .authorizeRequests().antMatchers("/logins.html",coreProperties.getBrowserProperties().getLoginPage()).permitAll().anyRequest().authenticated().and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
