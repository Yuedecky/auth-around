package com.broad.security.auth.core.social.qq.connect;

import com.broad.security.auth.core.social.qq.api.IQQ;
import com.broad.security.auth.core.social.qq.QQServiceProvider;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

public class QQConnectionFactory extends OAuth2ConnectionFactory<IQQ> {

    public QQConnectionFactory(String providerId, String appId,String appSecret) {
        super(providerId, new QQServiceProvider(appSecret, appId), new QQAdapter());
    }
}
