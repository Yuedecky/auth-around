package com.broad.security.auth.server.service.impl;

import com.broad.security.auth.server.domain.UserInfo;
import com.broad.security.auth.server.mapper.UserInfoMapper;
import com.broad.security.auth.server.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoSelective(String uid) {
        return userInfoMapper.getUserInfoById(uid);
    }
}
