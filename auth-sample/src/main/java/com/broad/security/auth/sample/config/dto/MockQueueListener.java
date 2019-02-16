package com.broad.security.auth.sample.config.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class MockQueueListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MockQueue mockQueue;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new Thread(() -> {
            while (true) {
                try {
                    Task<String> completeOrderTask = mockQueue.getTask();
                    if (completeOrderTask != null && completeOrderTask.getTaskNo() != null) {
                        log.info(Thread.currentThread().getName() + " listener success return order process result,orderNo:{}", completeOrderTask.getTaskNo());
                        completeOrderTask.getResult().setResult(completeOrderTask.getMessage());
                    }
                } catch (InterruptedException e) {
                    log.error("listener thread: {} gets task occurs error:{}", Thread.currentThread().getName(), e.getMessage());
                }
            }
        }).start();
    }

}
