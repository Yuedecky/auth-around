package com.broad.security.auth.core.web;

import com.broad.security.auth.core.properties.constants.AuthConstants;
import com.broad.security.auth.core.validate.ValidateCodeProcessorHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/code")
public class ValidateCodeController {

    @Resource
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    @RequestMapping(AuthConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX +"/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse httpServletResponse, @PathVariable String type) throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).createCode(new ServletWebRequest(request,httpServletResponse));
    }


}

