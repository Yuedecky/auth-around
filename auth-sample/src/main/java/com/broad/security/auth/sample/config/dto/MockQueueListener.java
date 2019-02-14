package com.broad.security.auth.sample.config.dto;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MockQueueListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new Thread(() -> {
            while (true) {
                if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())) {
                    String completeOrder = mockQueue.getCompleteOrder();
                    log.info(Thread.currentThread().getName() + ":return order process result,orderNo:{}", completeOrder);
                    deferredResultHolder.getContainer().get(completeOrder).setResult("success process your order request.");
                    mockQueue.setCompleteOrder(null);
                } else {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        log.error(Thread.currentThread().getName() + ":listening order completion occurs error:{}", e.getMessage());
                    }
                }
            }
        }).start();
    }

}
