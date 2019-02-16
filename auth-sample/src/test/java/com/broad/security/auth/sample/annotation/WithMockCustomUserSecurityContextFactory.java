package com.broad.security.auth.sample.annotation;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;


public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomerMockUser> {


    @Override
    public SecurityContext createSecurityContext(WithCustomerMockUser annotation) {
        //1.create empty security context
        SecurityContext sc = SecurityContextHolder.createEmptyContext();
        CustomerUserDetails principal = new CustomerUserDetails(annotation.name(), annotation.username());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "password", principal.getGrantedAuthorities());
        sc.setAuthentication(authentication);
        return sc;
    }


}
