package com.broad.security.auth.samples.auth.sample.service.impl;

import com.broad.security.auth.samples.auth.sample.constant.RoleConstant;
import com.broad.security.auth.samples.auth.sample.entity.UserEntity;
import com.broad.security.auth.samples.auth.sample.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Primary
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean insert(UserEntity userEntity) {
        String username = userEntity.getUsername();
        if (exist(username))
            return false;
        userEntity.setRoles(
                RoleConstant.ROLE_USER);
        int result = userMapper.insert(userEntity);
        return result == 1;
    }

    @Override
    public UserEntity getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 判断用户是否存在
     *
     * @param username 账号
     * @return 密码
     */
    private boolean exist(String username) {
        UserEntity userEntity = userMapper.selectByUsername(username);
        return (userEntity != null);
    }


}
