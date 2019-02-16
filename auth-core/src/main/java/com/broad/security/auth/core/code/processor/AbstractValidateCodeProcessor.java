package com.broad.security.auth.core.code.processor;

import com.broad.security.auth.core.code.BaseCode;
import com.broad.security.auth.core.code.generator.ValidateCodeRule;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

public abstract class AbstractValidateCodeProcessor<C extends BaseCode> implements ValidateCodeProcessor {
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private Map<String, ValidateCodeRule> validateCodeRules;

    @Override
    public void createCode(ServletWebRequest webRequest) throws Exception {
        C validateCode = generate(webRequest);
        saveCode(webRequest, validateCode);
        send(webRequest, validateCode);
    }

    protected abstract void send(ServletWebRequest request, C code) throws Exception;


    protected void saveCode(ServletWebRequest request, C code) {
        sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase(), code);
    }

    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getProcessorType(request);
        ValidateCodeRule validateCodeRule = validateCodeRules.get(type + "CodeGenerator");
        return (C) validateCodeRule.createRule(request);
    }

    /**
     * 获取请求验证码的类型
     *
     * @param request
     * @return
     */
    private String getProcessorType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/api/code/");
    }
}
