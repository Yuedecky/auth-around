package com.broad.security.auth.client.config;

import com.broad.security.auth.core.properties.BaseDataSourceConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import javax.sql.DataSource;


@Configuration
@EnableAuthorizationServer
public class OAuthClientConfiguration extends BaseDataSourceConfiguration {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.boot.profile:local}")
    private String profile;

    @Bean("clientDataSource")
    public DataSource clientDatasource() throws Exception {
        return super.baseDatasource(this.url, this.driverClassName, this.userName, this.password,"select * from client_user_info");
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(this.clientDatasource()).passwordEncoder(new BCryptPasswordEncoder()).withClient("clientApp").secret("123456").authorizedGrantTypes("client_credentials").scopes("devops");
    }




}
