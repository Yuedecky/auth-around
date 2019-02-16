package com.broad.security.auth.sample.controller;

import com.broad.security.auth.core.web.Response;
import com.broad.security.auth.sample.config.dto.AdminDto;
import com.broad.security.auth.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserService userService;

    @PostMapping("/")
    @ResponseBody
    public Response addAdmin(AdminDto adminDto){
        userService.saveAdminUser(adminDto);
        return Response.success(adminDto);
    }
}
