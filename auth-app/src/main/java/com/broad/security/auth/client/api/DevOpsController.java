package com.broad.security.auth.client.api;

import com.broad.security.auth.client.domain.ClientUserDetails;
import com.broad.security.auth.client.domain.ClientUserInfo;
import com.broad.security.auth.client.domain.OAuthToken;
import com.broad.security.auth.client.repository.UserRepository;
import com.broad.security.auth.client.service.impl.OAuthTokenServiceImpl;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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





}
