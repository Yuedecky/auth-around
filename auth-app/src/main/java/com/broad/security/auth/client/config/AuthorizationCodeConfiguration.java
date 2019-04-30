package com.broad.security.auth.client.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

@Component
public class AuthorizationCodeConfiguration {


    public String encodeCredentials(String name, String password) {
        String credentials = name + ":" + password;
        return new String(Base64.getEncoder().encode(credentials.getBytes()));
    }

    public MultiValueMap<String, String> getBody(String authorizationCode) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("scope", "read_user_info");
        formData.add("validate", authorizationCode);
        formData.add("redirect_uri", "http://localhost:9001/callback");
        return formData;
    }


    public HttpHeaders httpHeaders(String clientAuthorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic" + clientAuthorization);
        return headers;
    }

}
