package com.broad.security.auth.sample.config.customer;

import lombok.Data;

@Data
public class OAuthEnv {

    private String clientId;

    private String secret;

    private Integer tokenValidityInSeconds;

}
