package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ProductExceptionAdvice{

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> exceptionHandler(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("message", ex.getMessage());
        return error;
    }
}
