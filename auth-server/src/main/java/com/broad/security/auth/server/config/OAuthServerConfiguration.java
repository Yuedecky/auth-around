package com.broad.security.auth.server.config;

import com.broad.security.auth.core.config.BaseDataSourceConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@MapperScan(basePackages = BaseDataSourceConfiguration.BASE_PACKAGE_NAME, sqlSessionFactoryRef = "serverSqlSessionFactory")
public class OAuthServerConfiguration extends BaseDataSourceConfiguration {

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


    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;


    @Bean("serverDatasource")
    public DataSource serverDatasource() throws Exception {
        return super.baseDatasource(this.url, this.driverClassName, this.userName, this.password,"select * from user_info");
    }

    @Bean("serverSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        return super.baseSqlSessionFactory(this.serverDatasource(), this.profile, "com/broad/security/auth/server/mapper/*.mapper");
    }


    @Bean("serverSqlTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("serverSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return super.baseSqlSessionTemplate(sqlSessionFactory);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(this.serverDatasource()).withClient("clientApp").authorizedGrantTypes("authorization_code")
                .secret("123456").redirectUris("http://localhost:9001/callback").scopes("read_user_info", "read_contacts");
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager);
    }
}
