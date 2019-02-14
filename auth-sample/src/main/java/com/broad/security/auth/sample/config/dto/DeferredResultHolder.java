package com.broad.security.auth.sample.config.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class DeferredResultHolder {

    private Map<String, DeferredResult<String>> container = new HashMap<>();


}
