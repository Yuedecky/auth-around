package com.broad.security.auth.sample.controller;

import com.broad.security.auth.sample.config.dto.UserDto;
import com.broad.security.auth.sample.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping(value = "/{userId}")
    @ResponseBody
    public UserDto userDetail(@PathVariable String userId) {
        return userService.getUserDetail(userId);
    }
}
