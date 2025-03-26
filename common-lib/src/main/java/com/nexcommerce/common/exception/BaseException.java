package com.nexcommerce.common.exception;

import lombok.Getter;

/**
 * 基础异常类
 * 所有自定义异常的父类
 */
@Getter
public abstract class BaseException extends RuntimeException {
    
    private final String errorCode;
    
    public BaseException(String message) {
        super(message);
        this.errorCode = "INTERNAL_ERROR";
    }
    
    public BaseException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "INTERNAL_ERROR";
    }
    
    public BaseException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
