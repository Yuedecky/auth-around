package com.broad.security.auth.sample.annotation;

import com.broad.security.auth.sample.config.customer.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.Assert;

public class WithMockCustomUserSecurityContextFactoryAutowired implements WithSecurityContextFactory<WithCustomerMockUser> {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public SecurityContext createSecurityContext(WithCustomerMockUser annotation) {
        String username = annotation.username();
        Assert.hasLength(username, "username value() must be non-empty string");
        UserDetails principal = userDetailsService.loadUserByUsername(username);
        final Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext sc = SecurityContextHolder.createEmptyContext();
        sc.setAuthentication(authentication);
        return sc;
    }
}
