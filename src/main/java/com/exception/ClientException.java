package com.exception;

import lombok.Builder;
import lombok.Data;

/**
 * Created by luyi-netease on 2016/3/17.
 */
@Data
@Builder
public class ClientException extends Exception{

    private int errorCode;

    private String errorMessage;

}
