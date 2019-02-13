package com.broad.security.spring.roadmap.controller;

import com.broad.security.spring.roadmap.config.dto.UserDto;
import com.broad.security.spring.roadmap.service.HelloMessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/secure")
public class SecureController {

    @Resource
    private HelloMessageService helloMessageService;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public UserDto sayHello() {
        return helloMessageService.getMessage();
    }
}
