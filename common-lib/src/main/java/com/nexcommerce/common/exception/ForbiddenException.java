package com.nexcommerce.common.exception;

/**
 * 禁止访问异常
 * 当用户尝试访问没有权限的资源时抛出
 */
public class ForbiddenException extends BaseException {
    
    private static final String DEFAULT_ERROR_CODE = "FORBIDDEN";
    
    public ForbiddenException(String message) {
        super(message, DEFAULT_ERROR_CODE);
    }
    
    public ForbiddenException() {
        super("没有权限执行此操作", DEFAULT_ERROR_CODE);
    }
}
