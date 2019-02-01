package com.broad.security.auth.server.service;

import com.broad.security.auth.server.domain.UserInfo;

public interface IUserService {

    UserInfo getUserInfoSelective(String uid);
}
