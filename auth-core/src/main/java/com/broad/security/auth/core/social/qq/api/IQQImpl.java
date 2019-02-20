package com.broad.security.auth.core.social.qq.api;

import com.broad.security.auth.core.social.qq.QQUserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

public class IQQImpl extends AbstractOAuth2ApiBinding implements IQQ {


    private String appId;

    private String openId;

    public IQQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String result = getRestTemplate().getForObject(String.format(QQ_URL_GET_OPENID, accessToken), String.class);
        System.out.println(result);
        this.openId = StringUtils.substringBetween(result, "\"openid\":", "}");
    }

    private static final String QQ_URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private static final String QQ_URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?" +
            "access_token=%s&oauth_consumer_key=%s&openid=%s";

    @Override
    public QQUserInfo getQQUserInfo() {
        String getUserInfoUrl = String.format(QQ_URL_GET_USER_INFO, appId, openId);
        String result = getRestTemplate().getForObject(getUserInfoUrl, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(result, QQUserInfo.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
