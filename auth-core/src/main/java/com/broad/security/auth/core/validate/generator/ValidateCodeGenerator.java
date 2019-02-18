package com.broad.security.auth.core.validate.generator;

import com.broad.security.auth.core.properties.CoreProperties;
import com.broad.security.auth.core.validate.code.BaseCode;
import org.springframework.util.Assert;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {




    BaseCode generate(ServletWebRequest servletWebRequest);

    default void checkProperties(CoreProperties coreProperties) {
        Assert.notNull(coreProperties, "core constants cannot be null");
    }



}
