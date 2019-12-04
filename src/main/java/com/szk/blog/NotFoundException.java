package com.szk.blog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @program: blog
 * @description: 自定义异常类，找不到的异常
 * @author: sunzhongkai
 * @create: 2019-11-12 20:28
 **/
@ResponseStatus(HttpStatus.NOT_FOUND)//指定返回状态
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
