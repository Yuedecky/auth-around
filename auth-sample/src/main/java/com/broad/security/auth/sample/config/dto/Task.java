package com.broad.security.auth.sample.config.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * a task contains deferredResult's content
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task<T> {

    private DeferredResult<String> result;

    private T message;

    private boolean isTimeout = false;

    private String taskNo;

    public Task(String taskNo) {
        this.taskNo = taskNo;
    }
}
