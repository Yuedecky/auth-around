package com.broad.security.spring.roadmap.config.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Data
public class UserDto implements Serializable {

    private String name;

    private Collection<GrantedAuthority> roles;

    private String email;

}
