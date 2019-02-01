package com.broad.security.auth.server.api;

import com.broad.security.auth.server.domain.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {



    // 资源API
    @RequestMapping("/api/userinfo")
    public ResponseEntity<UserInfo> getUserInfo() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String email = user.getUsername() + "@spring2go.com";
        UserInfo userInfo = new UserInfo();
        userInfo.setName(user.getUsername());
        userInfo.setDetail(email);
        return ResponseEntity.ok(userInfo);
    }

}
