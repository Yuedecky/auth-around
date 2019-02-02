package com.broad.security.auth.client.api;

import com.broad.security.auth.client.domain.ClientUserDetails;
import com.broad.security.auth.client.domain.ClientUserInfo;
import com.broad.security.auth.client.domain.Entry;
import com.broad.security.auth.client.domain.OAuthToken;
import com.broad.security.auth.client.repository.UserRepository;
import com.broad.security.auth.client.service.impl.OAuthTokenServiceImpl;
import com.broad.security.auth.server.domain.UserInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;


@Controller
public class DevOpsController {

    @Autowired
    private OAuthTokenServiceImpl tokenService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public ModelAndView callback(String code,String state) {
        ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClientUserInfo clientUserInfo = userDetails.getClientUser();
        OAuthToken token = tokenService.getToken(code);
        clientUserInfo.setAccessToken(token.getAccessToken());
        clientUserInfo.setExpires(DateUtils.addMilliseconds(new Date(),Integer.parseInt(token.getExpiresIn())));
        userRepository.save(clientUserInfo);
        return new ModelAndView("redirect:/mainpage");
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        ClientUserInfo clientUser = userDetails.getClientUser();

        if (clientUser.getAccessToken() == null) {
            String authEndpoint = tokenService.getAuthorizationEndpoint();
            return new ModelAndView("redirect:" + authEndpoint);
        }

        clientUser.setEntries(Arrays.asList(
                new Entry("entry 1"),
                new Entry("entry 2")));

        ModelAndView mv = new ModelAndView("home");
        mv.addObject("user", clientUser);

        tryToGetUserInfo(mv, clientUser.getAccessToken());

        return mv;
    }

    private void tryToGetUserInfo(ModelAndView mv, String token) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);
        String endpoint = "http://localhost:8089/api/userinfo";
        try {
            ResponseEntity<UserInfo> userInfo = restTemplate.exchange(new RequestEntity<>(headers,HttpMethod.GET,URI.create(endpoint)),UserInfo.class);
            if (userInfo.getStatusCode().is2xxSuccessful()) {
                mv.addObject("userInfo", userInfo.getBody());
            } else {
                throw new RuntimeException("it was not possible to retrieve user profile");
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
