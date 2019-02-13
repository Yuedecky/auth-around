package com.broad.security.auth.sample.config.customer;

import com.broad.security.auth.sample.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordDetailService implements UserDetailsPasswordService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String password) {
        if (userDetails.isAccountNonExpired()) {
            userRepository.updatePasswordByNameCaseSensitive(userDetails.getUsername(), password);
            String username = userDetails.getUsername();
            return userDetailsService.loadUserByUsername(username);
        }
        return null;
    }
}
