package com.broad.security.auth.sample.service;

import com.broad.security.auth.sample.config.dto.AdminDto;
import com.broad.security.auth.sample.config.dto.UserDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
public class UserService {


    @PreAuthorize("authenticated")
    @SuppressWarnings("unchecked")
    public UserDto getUserDetail(String userId) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication != null) {
            final UserDto userDto = new UserDto();
            userDto.setName(authentication.getName());
            userDto.setRoles(new HashSet<>(authentication.getAuthorities()));
            return userDto;
        }
        return null;
    }

    public boolean saveAdminUser(AdminDto adminDto) {
        return true;
    }

}
