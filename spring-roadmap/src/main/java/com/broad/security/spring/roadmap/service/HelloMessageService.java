package com.broad.security.spring.roadmap.service;

import com.broad.security.spring.roadmap.config.dto.UserDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class HelloMessageService {


    @PreAuthorize("authenticated")
    @SuppressWarnings("unchecked")
    public UserDto getMessage() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication != null) {
            final UserDto userDto = new UserDto();
            userDto.setName(authentication.getName());
            userDto.setRoles((Collection<GrantedAuthority>) authentication.getAuthorities());
            return userDto;
        }
        return null;
    }

}
