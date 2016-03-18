package com.exception;

import lombok.Builder;
import lombok.Data;

/**
 * Created by luyi-netease on 2016/3/17.
 */
@Data
@Builder
public class ClientException extends Exception{

    /**
     * @see ClientErrorType
     * 错误码
     */
    private int errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

}
