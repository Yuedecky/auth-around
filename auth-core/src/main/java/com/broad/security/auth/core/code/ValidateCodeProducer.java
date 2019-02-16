package com.broad.security.auth.core.code;

import com.broad.security.auth.core.config.properties.CoreProperties;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeProducer {
    ImageCode createImage(ServletWebRequest servletWebRequest);

    void setCoreProperties(CoreProperties coreProperties);
}
