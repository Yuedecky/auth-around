package com.broad.security.spring.roadmap.config.customer;

import lombok.Data;

@Data
public class OAuthEnv {

    private String clientId;

    private String secret;

    private Integer tokenValidityInSeconds;
}
