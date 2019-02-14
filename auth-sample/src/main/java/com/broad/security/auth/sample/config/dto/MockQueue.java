package com.broad.security.auth.sample.config.dto;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Getter
@Slf4j
@Component
public class MockQueue {

    private String placeOrder;

    private String completeOrder;

    public void setPlaceOrder(String placeOrder) {
        new Thread(() -> {
            try {
                log.info(Thread.currentThread().getName() + ":accept create order request,orderNo:{}", placeOrder);
                long start = System.currentTimeMillis();
                TimeUnit.SECONDS.sleep(1);
                this.completeOrder = placeOrder;
                log.info(Thread.currentThread().getName() + ":create order process finished,orderNo: {},cost {} ms", completeOrder, System.currentTimeMillis() - start);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }).start();


    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
