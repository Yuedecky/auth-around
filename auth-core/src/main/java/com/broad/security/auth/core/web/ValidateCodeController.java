package com.broad.security.auth.core.web;

import com.broad.security.auth.core.code.ImageCode;
import com.broad.security.auth.core.code.SmsCode;
import com.broad.security.auth.core.code.generator.ImageCodeGenerator;
import com.broad.security.auth.core.code.generator.ValidateCodeRule;
import com.broad.security.auth.core.code.processor.impl.ImageCodeProcessor;
import com.broad.security.auth.core.code.processor.impl.SmsCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/code")
public class ValidateCodeController {

    public static final String SESSION_KEY_IMAGE_CODE = "SESSION_KEY_IMAGE_CODE";

    public static final String SESSION_KEY_SMS_CODE = "SESSION_KEY_SMS_CODE";



    @Autowired
    private ImageCodeProcessor imageCodeProcessor;


    @Autowired
    private SmsCodeProcessor smsCodeProcessor;

    @RequestMapping("/image")
    public void createImageCode(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
        ServletWebRequest webRequest = new ServletWebRequest(request, httpServletResponse);
        imageCodeProcessor.createCode(webRequest);
    }


    @RequestMapping("/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletWebRequest webRequest = new ServletWebRequest(request,response);
        smsCodeProcessor.createCode(webRequest);
    }
}

