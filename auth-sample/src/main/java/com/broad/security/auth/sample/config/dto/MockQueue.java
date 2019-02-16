package com.broad.security.auth.sample.config.dto;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@Getter
@Slf4j
@Component
public class MockQueue {

    private BlockingQueue<Task<String>> receiveQueue = new LinkedBlockingDeque<>();

    private BlockingQueue<Task<String>> completeQueue = new LinkedBlockingDeque<>();

    /**
     * put a task into receiveQueue
     *
     * @param task
     */
    public void putTask(Task<String> task) {
        try {
            receiveQueue.put(task);
        } catch (InterruptedException e) {
            log.error("thread:{} put task into receiveQueue occurs error:{}", Thread.currentThread().getName(), e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * @return
     * @throws InterruptedException
     */
    public Task<String> getTask() throws InterruptedException {
        try {
            return completeQueue.take();
        } catch (InterruptedException e) {
            log.error("thread:{} take task from completeQueue occurs error:{}", Thread.currentThread().getName(), e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * execute long time task
     */
    public void execute() {
        new Thread(() -> {
            while (true) {
                try {
                    Task<String> task = receiveQueue.take();
                    log.info("the mock queue received queue,processing... taskNo:{}", task.getTaskNo());
                    TimeUnit.MILLISECONDS.sleep(500);
                    task.setMessage("taskNo:" + task.getTaskNo() + " success");
                    if (task.isTimeout()) {
                        log.error("task timeout,taskNo:{},will be interrupted", task.getTaskNo());
                    }
                    log.info(Thread.currentThread().getName() + " the mock queue has processed successful");
                    completeQueue.put(task);
                } catch (InterruptedException e) {
                    log.error("thread:{} execute occurs error:{}", Thread.currentThread().getName(), e.getMessage());
                }
            }
        }).start();
    }

}
