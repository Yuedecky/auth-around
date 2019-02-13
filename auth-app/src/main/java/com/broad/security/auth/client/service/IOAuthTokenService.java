package com.broad.security.auth.client.service;

import com.broad.security.auth.client.domain.OAuthToken;

public interface IOAuthTokenService {


    /**
     *
     * @param authorizationCode
     * @return
     */
    OAuthToken getToken(String authorizationCode);


}
