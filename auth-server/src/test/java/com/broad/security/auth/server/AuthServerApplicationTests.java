package com.broad.security.auth.server;

import com.broad.security.auth.server.config.OAuthSecurityConfiguration;
import com.broad.security.auth.server.domain.UserInfo;
import com.broad.security.auth.server.mapper.UserInfoMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.configuration.ObjectPostProcessorConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@Import({ObjectPostProcessorConfiguration.class, AuthenticationConfiguration.class})
@SpringBootTest(classes = OAuthSecurityConfiguration.class)
public class AuthServerApplicationTests {


    @Resource
    private UserInfoMapper userInfoMapper;


    @Test
    public void contextLoads() {
    }


    @Test
    public void testAddUser() {
        final UserInfo info = new UserInfo();
        info.setId(RandomStringUtils.randomAlphabetic(20));
        info.setName("broad");
        info.setRegisterTime(new Date());
        info.setDeleted(false);
        info.setPassword("12222");
        info.setAvatar("http://res.51so.info/FpWknl0cjYQCuVGzC2GOoXax_v5N_profile.60X60");
        userInfoMapper.addUser(info);
    }

    @Test
    public void testSelectById() {
        UserInfo info = userInfoMapper.getUserInfoById("feCtdMKlWhsgLAoTjVkx");
        Assert.assertNotNull(info);
        Assert.assertEquals("broad",info.getName());
    }


}

