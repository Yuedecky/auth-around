package com.broad.security.auth.sample.controller;

import com.broad.security.auth.sample.config.dto.DeferredResultHolder;
import com.broad.security.auth.sample.config.dto.MockQueue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@RestController(value = "/hello/")
@Slf4j
public class OpenController {
    @Resource
    private MockQueue mockQueue;

    @Resource
    private DeferredResultHolder deferredResultHolder;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DeferredResult<String> sayHello() {
        log.info("main thread starts");
        String orderNo = RandomStringUtils.randomAlphabetic(16);
        mockQueue.setPlaceOrder(orderNo);
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getContainer().put(orderNo, result);
        log.info("main thread ends");
        return result;
    }

    private Callable<String> childProcess() {
        return () -> {
            log.info("child thread starts");
            TimeUnit.SECONDS.sleep(1);
            log.info("child thread ends");
            return "child hello";
        };
    }


}
