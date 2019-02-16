package com.broad.security.auth.sample;

import com.alibaba.fastjson.JSON;
import com.broad.security.auth.sample.annotation.WithMockAdmin;
import com.broad.security.auth.sample.config.dto.AdminDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.securityContext;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserControllerTests {


    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    /**
     * 构建mvc对象
     */
    @Before
    public void setup() {
        //SecurityMockMvcConfigurers.springSecurity()
        //will perform all of the initial setup we need to integrate Spring Security with Spring MVC Test
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }


    /**
     * 测试/user接口
     * @throws Exception
     */
    @Test
    public void whenQuerySuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status()
                .is(HttpStatus.UNAUTHORIZED.value()));
//        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
    }


    @Test
    public void order() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/hello/order").contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void withMockCustomerUser() {

    }

    @Test
    @WithMockAdmin
    public void withMockAdmin() throws Exception {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            AdminDto dto = new AdminDto();
            dto.setUsername(user.getUsername());
            dto.setPassword(user.getPassword());
            Collection<GrantedAuthority> grantedAuthorities = user.getAuthorities();
            Set<String> roles = new HashSet<>();
            for (GrantedAuthority authority : grantedAuthorities) {
                roles.add(authority.getAuthority());
            }
            dto.setRoles(roles);
            mockMvc.perform(MockMvcRequestBuilders.post("/admin/")
                    .with(securityContext(securityContext)).contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(dto))).andExpect(unauthenticated());
        }
    }


}
