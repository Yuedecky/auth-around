package com.broad.security.auth.core.social.qq.config;

import com.broad.security.auth.core.properties.CoreProperties;
import com.broad.security.auth.core.properties.QQProperties;
import com.broad.security.auth.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

@Configuration
@ConditionalOnProperty(prefix = "auth.security.social.qq", name = "app-id")
public class QQApiConfiguration extends SocialAutoConfigurerAdapter {

    @Autowired
    private CoreProperties coreProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties = coreProperties.getSocial().getQq();
        return new QQConnectionFactory(qqProperties.getProviderId(), qqProperties.getAppId(), qqProperties.getAppSecret());
    }
}
