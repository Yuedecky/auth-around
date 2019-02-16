package com.broad.security.auth.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.broad.security.auth")
public class AuthSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthSampleApplication.class, args);
    }

}

