package com.broad.security.auth.client.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

@Data
public class ClientUserDetails implements UserDetails {

    private ClientUserInfo clientUserInfo;


    public ClientUserDetails(ClientUserInfo user) {
        this.clientUserInfo = user;
    }

    public ClientUserInfo getClientUser() {
        return clientUserInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public String getPassword() {
        return clientUserInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return clientUserInfo.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
