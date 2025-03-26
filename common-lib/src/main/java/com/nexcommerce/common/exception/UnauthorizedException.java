package com.nexcommerce.common.exception;

/**
 * 未授权异常
 * 当用户未登录或身份验证失败时抛出
 */
public class UnauthorizedException extends BaseException {
    
    private static final String DEFAULT_ERROR_CODE = "UNAUTHORIZED";
    
    public UnauthorizedException(String message) {
        super(message, DEFAULT_ERROR_CODE);
    }
    
    public UnauthorizedException() {
        super("未授权，请先进行身份验证", DEFAULT_ERROR_CODE);
    }
}
