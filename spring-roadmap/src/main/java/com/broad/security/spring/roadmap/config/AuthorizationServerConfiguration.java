package com.broad.security.spring.roadmap.config;

import com.broad.security.spring.roadmap.config.customer.OAuthEnv;
import com.broad.security.spring.roadmap.config.enums.AuthorityEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter implements EnvironmentAware {

    private static final String ENV_OAUTH = "authentication.oauth";

    private BindResult<OAuthEnv> binder;

    @Override
    public void setEnvironment(Environment environment) {
        this.binder = Binder.get(environment).bind(ENV_OAUTH, OAuthEnv.class);
    }


    @Autowired
    private DataSource dataSource;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        OAuthEnv env = binder.get();
        clients.inMemory()
                .withClient(env.getClientId())
                .scopes("read", "write")
                .authorities(AuthorityEnum.ROLE_ADMIN.name(), AuthorityEnum.ROLE_USER.name())
                .authorizedGrantTypes("password", "refresh_token")
                .secret(env.getSecret())
                .accessTokenValiditySeconds(env.getTokenValidityInSeconds());
    }


}
