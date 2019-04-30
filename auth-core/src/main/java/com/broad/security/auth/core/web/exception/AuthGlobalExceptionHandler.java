package com.broad.security.auth.core.web.exception;

import com.broad.security.auth.core.web.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestController
@ControllerAdvice
@Slf4j
public class AuthGlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Value(("spring.profiles.active"))
    private String profile;

    @ExceptionHandler
    public void defaultHandler(HttpServletRequest servletRequest, Exception e) {
        handleExceptionInternal(e, null, null, HttpStatus.BAD_REQUEST, new ServletWebRequest(servletRequest));
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        log.error("全局异常处理器捕获到异常:{}", ex.getMessage());
        if (ex instanceof HttpMessageConversionException) {
            return new ResponseEntity<>(Response.error("参数转换异常:" + ex.getMessage()), status);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return new ResponseEntity<>(Response.error("方法参数不合法:" + ex.getMessage()), status);
        }
        if (ex instanceof NullPointerException) {
            return new ResponseEntity<>(Response.error("空指针异常"), status);
        }
        return new ResponseEntity<>(Response.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
