package com.broad.security.auth.sample.config.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserDto implements Serializable {

    private String name;

    private Set<GrantedAuthority> roles;

    private String email;

}
