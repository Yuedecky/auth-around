package com.broad.security.auth.core.social.qq;

import com.broad.security.auth.core.social.qq.api.IQQ;
import com.broad.security.auth.core.social.qq.api.IQQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<IQQ> {

    private String appId;

    private static final String QQ_URL_AUTHENTICATION = "https://graph.qq.com/oauth2.0/authorize";


    private static final String QQ_URL_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appSecret, String appId) {
        super(new OAuth2Template(appId, appSecret, QQ_URL_AUTHENTICATION, QQ_URL_TOKEN));
    }

    @Override
    public IQQ getApi(String accessToken) {
        return new IQQImpl(accessToken,appId);
    }
}
