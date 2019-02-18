package com.broad.security.auth.core.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    // ~ Instance fields
    // ================================================================================================

    private final Object principal;

    // ~ Constructors
    // ===================================================================================================

    /**
     * This constructor can be safely used by any validate that wishes to create a
     * <validate>UsernamePasswordAuthenticationToken</validate>, as the {@link #isAuthenticated()}
     * will return <validate>false</validate>.
     *
     */
    public SmsCodeAuthenticationToken(String mobile) {
        super(null);
        this.principal = mobile;
    }

    /**
     * This constructor should only be used by <validate>AuthenticationManager</validate> or
     * <validate>AuthenticationProvider</validate> implementations that are satisfied with
     * producing a trusted (i.e. {@link #isAuthenticated()} = <validate>true</validate>)
     * authentication token.
     *
     * @param principal
     * @param authorities
     */
    public SmsCodeAuthenticationToken(Object principal,
                                               Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
    }



    // ~ Methods
    // ========================================================================================================


    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
