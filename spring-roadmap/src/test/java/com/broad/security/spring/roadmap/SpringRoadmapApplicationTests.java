package com.broad.security.spring.roadmap;

import com.broad.security.spring.roadmap.config.dto.UserDto;
import com.broad.security.spring.roadmap.dao.UserRepository;
import com.broad.security.spring.roadmap.domain.User;
import com.broad.security.spring.roadmap.service.HelloMessageService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRoadmapApplicationTests {

    @Resource
    private HelloMessageService helloMessageService;

    @Resource
    private UserRepository userRepository;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void contextLoads() {
        helloMessageService.getMessage();
    }

    @Test
    @WithMockUser(username = "default")
    public void testWithMockUser() {
        Assert.assertNotNull(helloMessageService.getMessage());
    }


    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsService")
    public void testWithUserDetails() {
        UserDto userDto = helloMessageService.getMessage();
        Assert.assertNotNull(userDto);
    }


    @Test
    public void testDaoUser() {
        User user = userRepository.findByUsernameCaseInsensitive("admin");
        Assert.assertNotNull(user);
    }
}

