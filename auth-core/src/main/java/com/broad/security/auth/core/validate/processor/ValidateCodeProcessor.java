package com.broad.security.auth.core.validate.processor;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeProcessor {

    String SESSION_KEY_PREFIX = "SESSION_KEY_CODE_";

    void createCode(ServletWebRequest webRequest) throws Exception;

    /**
     * 校验验证码
     *
     * @param servletWebRequest
     * @throws Exception
     */
    void validate(ServletWebRequest servletWebRequest);

}
