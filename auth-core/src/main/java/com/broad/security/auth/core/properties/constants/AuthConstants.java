package com.broad.security.auth.core.properties.constants;

public interface AuthConstants {

    /**
     * 默认的处理验证码的url前缀
     */
    public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
    /**
     * 当请求需要身份认证时，默认跳转的url
     *
     * @see com.broad.security.auth.core.web.ValidateCodeController
     */
    public static final String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";
    /**
     * 默认的用户名密码登录请求处理url
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";
    /**
     * 默认的手机验证码登录请求处理url
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authentication/mobile";


    /**
     * 手机号验证请求中属性
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

    /**
     *tupian 验证请求中属性
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";


    public static final String DEFAULT_UNAUTHENTICATION_PAGE = "/api/authentication/require";

}
