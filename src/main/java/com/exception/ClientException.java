package com.exception;

/**
 * Created by luyi-netease on 2016/3/17.
 */
public class ClientException extends Exception{

    private int errorCode;

    private String errorMessage;

    public ClientException(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
