package com.broad.security.auth.sample;

import com.broad.security.auth.sample.dao.UserRepository;
import com.broad.security.auth.sample.service.UserService;
import com.broad.security.auth.sample.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthSampleApplicationTests {

    @Resource
    private UserService helloMessageService;

    @Resource
    private UserRepository userRepository;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void contextLoads() {
        helloMessageService.getUserDetail("1");
    }






    @Test
    public void testDaoUser() {
        User user = userRepository.findByUsernameCaseInsensitive("admin");
        Assert.assertNotNull(user);
    }
}

