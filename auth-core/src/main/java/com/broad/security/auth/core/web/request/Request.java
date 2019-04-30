package com.broad.security.auth.core.web.request;

import lombok.Data;

import javax.validation.Valid;

@Data
public class Request<T> {
    @Valid
    private T data;
}
