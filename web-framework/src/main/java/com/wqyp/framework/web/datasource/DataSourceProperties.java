package com.wqyp.framework.web.datasource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.datasource")
@Setter
@Getter
@Component
public class DataSourceProperties {

    private String url;
    private String driverClassName;
    private String username;
    private String password;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private boolean removeAbandoned;
    private int removeAbandonedTimeout;
    private boolean logAbandoned;
    private String proxyFilters;
    private int slowSqlMillis;
    private boolean logSlowSql;
    private boolean mergeSql;
    private String loginDruidUsername;
    private String loginDruidPassword;
    private boolean logViolation;
    private boolean throwException;


}
