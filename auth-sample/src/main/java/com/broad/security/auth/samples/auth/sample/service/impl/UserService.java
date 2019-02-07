package com.broad.security.auth.samples.auth.sample.service.impl;

import com.broad.security.auth.samples.auth.sample.entity.UserEntity;

public interface UserService {
    /**
     * 添加新用户
     *
     * username 唯一， 默认 USER 权限
     */
    boolean insert(UserEntity userEntity);

    /**
     * 查询用户信息
     * @param username 账号
     * @return UserEntity
     */
    UserEntity getByUsername(String username);
}
