package com.broad.security.auth.core.web;

import com.broad.security.auth.core.code.processor.impl.ImageCodeProcessor;
import com.broad.security.auth.core.code.processor.impl.SmsCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/code")
public class ValidateCodeController {


    @Autowired
    private ImageCodeProcessor imageCodeProcessor;


    @Autowired
    private SmsCodeProcessor smsCodeProcessor;

    @RequestMapping("/image")
    public void createImageCode(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
        ServletWebRequest webRequest = new ServletWebRequest(request, httpServletResponse);
        imageCodeProcessor.createCode(webRequest);
    }


    @GetMapping(value = "/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletWebRequest webRequest = new ServletWebRequest(request,response);
        smsCodeProcessor.createCode(webRequest);
    }
}

