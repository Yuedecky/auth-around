package com.broad.security.auth.sample.controller;

import com.broad.security.auth.sample.config.dto.MockQueue;
import com.broad.security.auth.sample.config.dto.Task;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/hello")
@Slf4j
public class OpenController {


    @Autowired
    private MockQueue mockQueue;

    @RequestMapping(value = "/no",method = RequestMethod.GET)
    public DeferredResult<String> sayHello() {
        log.info("sayHello's main thread starts");
        String orderNo = RandomStringUtils.randomAlphabetic(16);
        Task<String> taskOrder = new Task<>(orderNo);
        DeferredResult<String> result = new DeferredResult<>(2000L);
        taskOrder.setResult(result);
        result.onTimeout(() -> {
            log.error("task timeout,taskNo:{}", orderNo);
            taskOrder.setTimeout(true);
            result.setErrorResult("task timeout,taskNo:" + orderNo);
        });
        mockQueue.putTask(taskOrder);
        mockQueue.execute();
        log.info("sayHello's main thread ends");
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
