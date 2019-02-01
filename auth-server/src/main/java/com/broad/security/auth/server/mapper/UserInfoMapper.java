package com.broad.security.auth.server.mapper;

import com.broad.security.auth.server.domain.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {

    @Select("select * from user_info where id=#{id} and deleted=0")
    UserInfo getUserInfoById(String id);


    @Insert("insert into user_info (id,name,password,detail,create_time,avatar) values " +
            "(#{user.id},#{user.name},#{user.password},#{user.detail},#{user.registerTime},#{user.avatar})")
    void addUser(@Param("user") UserInfo userInfo);

}
