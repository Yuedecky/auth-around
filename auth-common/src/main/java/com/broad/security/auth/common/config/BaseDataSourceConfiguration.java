package com.broad.security.auth.common.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@MapperScan(basePackages = BaseDataSourceConfiguration.BASE_PACKAGE_NAME,sqlSessionFactoryRef = BaseDataSourceConfiguration.SQL_SESSION_FACTORY_NAME)
public class BaseDataSourceConfiguration extends AuthorizationServerConfigurerAdapter {

    public static final String BASE_PACKAGE_NAME = "com.broad.security.common.mapper";


    public static final String SQL_SESSION_FACTORY_NAME = "commonSqlSessionFactory";


    public DataSource baseDatasource(String url,String driverClassName,String userName,String password,String connectionTestQuery) throws SQLException {
        if (StringUtils.isEmpty(url)) {
            throw new ApplicationContextException("Default s Database connection pool is not configured correctly");
        }
        HikariDataSource druidDataSource = new HikariDataSource();
        druidDataSource.setJdbcUrl(url);
        druidDataSource.setConnectionTestQuery(connectionTestQuery);
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }


    public SqlSessionTemplate baseSqlSessionTemplate(SqlSessionFactory reportSqlSessionFactory) {
        return new SqlSessionTemplate(reportSqlSessionFactory);
    }



    public PlatformTransactionManager baseTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    public SqlSessionFactory baseSqlSessionFactory(DataSource dataSource,String profile,String mapperLocation) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.broad.security.auth.server.domain");
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setDefaultStatementTimeout(120); //statement超时时间（秒）
        sqlSessionFactoryBean.setConfiguration(configuration);
        //添加插件：分页(PaginationInterceptor)、性能分析(PerformanceInterceptor)、乐观锁(OptimisticLockerInterceptor)、禁止全表update和delete(fullTableOperationInterceptor)
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(paginationInterceptor);
        if ("local".equals(profile)) {
            interceptors.add(new PerformanceInterceptor());
        }
        sqlSessionFactoryBean.setPlugins(interceptors.toArray(new Interceptor[0]));
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocation));
        return sqlSessionFactoryBean.getObject();
    }

}
