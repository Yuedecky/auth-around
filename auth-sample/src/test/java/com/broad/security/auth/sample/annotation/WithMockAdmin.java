package com.broad.security.auth.sample.annotation;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "admin",password = "admin",roles = {"USER","ADMIN"})
public @interface WithMockAdmin {
}
