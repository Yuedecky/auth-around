package com.broad.security.auth.sample.config.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class AdminDto implements Serializable {

    private String username;

    private String password;

    private Set<String> roles;
}
