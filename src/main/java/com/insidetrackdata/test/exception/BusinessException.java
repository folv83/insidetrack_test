package com.insidetrackdata.test.exception;

public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
    
    public BusinessException(String message){
        super(message);
    }

    public BusinessException(String message, Throwable ex){
        super(message, ex);
    }    
    
}
