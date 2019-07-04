package com.wqyp.framework.web.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DatasourceBaseConfigure {

    @Autowired
    private DataSourceProperties dataSourceProperties;


    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean("dataSource")
    protected DruidDataSource createDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        BeanUtils.copyProperties(dataSource, this.dataSourceProperties);
        dataSource.setUrl(this.url);
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setPassword(this.password);
        dataSource.setUsername(this.username);
        dataSource.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return dataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        //PerformanceInterceptor(),OptimisticLockerInterceptor()
        //添加分页功能
        Interceptor page = paginationInterceptor();
        List<Interceptor> interceptors = new ArrayList<>(1);
        interceptors.add(page);
        if (profile.equals("dev") || profile.equals("test")) {
            interceptors.add(performanceInterceptor());
        }
        Interceptor[] plugins = new Interceptor[interceptors.size()];
        sqlSessionFactory.setPlugins(interceptors.toArray(plugins));
        sqlSessionFactory.setGlobalConfig(globalConfiguration());
        return sqlSessionFactory.getObject();
    }


    @Bean
    public GlobalConfig globalConfiguration() {
        GlobalConfig conf = new GlobalConfig();
        conf.setSqlInjector(new LogicSqlInjector());
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setLogicNotDeleteValue("1");
        dbConfig.setIdType(IdType.AUTO);
        conf.setDbConfig(dbConfig);
        conf.setMetaObjectHandler(new MyMetaObjectHandler());
        return conf;
    }


    @Bean
    @Profile({"dev", "test"}) //设置dev test环境开启性能
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        /*<!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->*/
        performanceInterceptor.setMaxTime(4000);
        /*<!--SQL是否格式化 默认false-->*/
        performanceInterceptor.setFormat(true);
        performanceInterceptor.setWriteInLog(true);
        return performanceInterceptor;
    }


    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        page.setSqlParser(new JsqlParserCountOptimize());
        return page;
    }


}
