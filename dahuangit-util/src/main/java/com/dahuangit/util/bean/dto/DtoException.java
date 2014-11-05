package com.dahuangit.util.bean.dto;

/**
 *  Dto相关的异常.
 * @author 黄仁良
 *
 */
public class DtoException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private String message;
    private Throwable cause;
    
    public DtoException(String message) {
        super(message);
        this.message = message;
    }
    
    public DtoException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    public String toString() {
        return "DtoException: [cause=" + cause + ", message=" + message + "]";
    }
}
