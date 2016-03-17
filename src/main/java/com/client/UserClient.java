package com.client;

import org.apache.catalina.websocket.WsOutbound;

/**
 * Created by luyi-netease on 2016/3/17.
 */
public class UserClient {

    private com.message.UserClient messageInbound;

    private WsOutbound wsOutbound;

    private String username;

    public UserClient(String username, com.message.UserClient messageInbound, WsOutbound wsOutbound){
        this.username = username;
        this.messageInbound = messageInbound;
        this.wsOutbound =wsOutbound;
    }

    public String getUsername(){ return this.username; }

    public void sendMessage(String message){

    }
}
