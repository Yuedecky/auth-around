package com.broad.security.auth.core.validate.processor;

import com.broad.security.auth.core.validate.ValidateCodeType;
import com.broad.security.auth.core.validate.code.BaseCode;
import com.broad.security.auth.core.validate.generator.ValidateCodeGenerator;
import com.broad.security.auth.core.web.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

public abstract class AbstractValidateCodeProcessor<C extends BaseCode> implements ValidateCodeProcessor {
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeRules;

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
        ValidateCodeGenerator validateCodeGenerator = validateCodeRules.get(type + "CodeGenerator");
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 获取请求验证码的类型
     *
     * @param request
     * @return
     */
    private String getProcessorType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/api/validate/");
    }

    private ValidateCodeType getValidateCodeType(ServletWebRequest servletWebRequest) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "codeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    private String getSessionKey(ServletWebRequest servletWebRequest) {
        return SESSION_KEY_PREFIX + getProcessorType(servletWebRequest).toUpperCase();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType processorType = getValidateCodeType(request);
        String sessionKey = getSessionKey(request);

        C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(processorType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }

}
