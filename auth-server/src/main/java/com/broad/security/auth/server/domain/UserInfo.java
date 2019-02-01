package com.broad.security.auth.server.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserInfo {

    private String id;

    private String name;

    private String avatar;

    private String password;

    private Date registerTime;

    private String detail;

    private Boolean deleted;

    private String slat;

    private String encryption;


}
