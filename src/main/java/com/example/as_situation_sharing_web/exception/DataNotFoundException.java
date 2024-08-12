package com.example.as_situation_sharing_web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "엔티티를 찾을수 없음")
public class DataNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public DataNotFoundException(String msg){
        super(msg);
    }
}
