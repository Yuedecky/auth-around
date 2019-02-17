package com.broad.security.auth.core.mobile.provider;

import com.broad.security.auth.core.mobile.SmsCodeAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    // ~ Static fields/initializers
    // =====================================================================================

    private UserDetailsService userDetailsService;

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof SmsCodeAuthenticationToken) {
            SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken) authentication;
            UserDetails userDetails = userDetailsService.loadUserByUsername((String) smsCodeAuthenticationToken.getPrincipal());
            if (userDetails == null) {
                throw new InternalAuthenticationServiceException("读取不到用户信息");
            } else {
                SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(userDetails.getUsername(), userDetails.getAuthorities());
                authenticationResult.setDetails(smsCodeAuthenticationToken.getDetails());
                return authentication;
            }
        }
        throw new InternalAuthenticationServiceException("验证手机号内部异常");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
