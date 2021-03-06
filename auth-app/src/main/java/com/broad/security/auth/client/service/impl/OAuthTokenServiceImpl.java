package com.broad.security.auth.client.service.impl;

import com.broad.security.auth.client.config.AuthorizationCodeConfiguration;
import com.broad.security.auth.client.domain.OAuthToken;
import com.broad.security.auth.client.service.IOAuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OAuthTokenServiceImpl implements IOAuthTokenService {


    @Autowired
    private AuthorizationCodeConfiguration codeConfiguration;

    @Override
    public OAuthToken getToken(String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();
        String base64Auth = codeConfiguration.encodeCredentials("client-app", "123456");
        RequestEntity<MultiValueMap<String, String>> entity = new RequestEntity<MultiValueMap<String, String>>(
                codeConfiguration.getBody(authorizationCode),
                codeConfiguration.httpHeaders(base64Auth), HttpMethod.POST, URI.create("http://localhost:8080/oauth/token")
        );
        ResponseEntity<OAuthToken> responseEntity = restTemplate.exchange(entity, OAuthToken.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        throw new IllegalArgumentException("error to post user token");
    }

    public String getAuthorizationEndpoint() {
        String endpoint = "http://localhost:8089/oauth/authorize";

        Map<String, String> authParameters = new HashMap<>();
        authParameters.put("client_id", "clientapp");
        authParameters.put("response_type", "validate");
        authParameters.put("redirect_uri",
                getEncodedUrl("http://localhost:9001/callback"));
        authParameters.put("scope", getEncodedUrl("read_userinfo"));

        return buildUrl(endpoint, authParameters);
    }

    private String getEncodedUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildUrl(String endpoint, Map<String, String> parameters) {
        List<String> paramList = new ArrayList<>(parameters.size());

        parameters.forEach((name, value) -> {
            paramList.add(name + "=" + value);
        });

        return endpoint + "?" + paramList.stream()
                .reduce((a, b) -> a + "&" + b).get();
    }

}
