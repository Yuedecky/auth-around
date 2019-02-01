package com.broad.security.auth.client.service.impl;

import com.broad.security.auth.client.domain.ClientUserDetails;
import com.broad.security.auth.client.domain.ClientUserInfo;
import com.broad.security.auth.client.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<ClientUserInfo> optionalUser = userRepository.findByName(name);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("invalid username or password");
        }
        return new ClientUserDetails(optionalUser.get());
    }
}
