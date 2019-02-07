package com.broad.security.auth.samples.auth.sample.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
@Data
public class UserEntity {

    private String username;

    private String password;

    private String roles;

    private List<String> permissions;

}
