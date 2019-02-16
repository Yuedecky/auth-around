package com.broad.security.auth.core.code.generator;

import com.broad.security.auth.core.code.BaseCode;
import com.broad.security.auth.core.config.properties.CoreProperties;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeRule {
    BaseCode createRule(ServletWebRequest servletWebRequest);

    void setCoreProperties(CoreProperties coreProperties);
}
