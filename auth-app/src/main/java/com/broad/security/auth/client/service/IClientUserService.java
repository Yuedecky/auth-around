package com.broad.security.auth.client.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IClientUserService {
    UserDetails loadUserByName(String name);

}
