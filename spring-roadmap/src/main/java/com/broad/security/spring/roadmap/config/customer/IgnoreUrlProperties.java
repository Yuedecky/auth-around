package com.broad.security.spring.roadmap.config.customer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
@Data
public class IgnoreUrlProperties {

    @Value("${authentication.oauth.ignoreUrls}")
    private String[] ignoreUrls;

    @Value("${server.servlet.context-path}")
    private String prefix;

}
