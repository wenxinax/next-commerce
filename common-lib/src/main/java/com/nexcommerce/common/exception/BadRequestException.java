package com.nexcommerce.common.exception;

/**
 * 错误请求异常
 * 当请求参数错误或者请求无法被处理时抛出
 */
public class BadRequestException extends BaseException {
    
    private static final String DEFAULT_ERROR_CODE = "BAD_REQUEST";
    
    public BadRequestException(String message) {
        super(message, DEFAULT_ERROR_CODE);
    }
    
    public BadRequestException(String message, Throwable cause) {
        super(message, DEFAULT_ERROR_CODE, cause);
    }
}
