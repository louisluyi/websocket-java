package com.server;

import com.exception.ClientErrorType;
import com.exception.ClientException;
import com.client.UserClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luyi-netease on 2016/3/17.
 */
public class ContactServer {

    private static List<UserClient> userClients = new ArrayList();

    private static int clientId = 0;

    private static ContactServer contactServer = null;

    private ContactServer(){}

    public static ContactServer getInstance(){
        if(contactServer == null){
            contactServer = new ContactServer();
        }
        return contactServer;
    }

    public void addUserClient(UserClient userClient) throws ClientException{
        String username = userClient.getUsername();
        if(username == null || username.equals("")){
            throw new ClientException(ClientErrorType.systemError, "用户名不能为空");
        }
        if(isUserExisted(username)){
            throw new ClientException(ClientErrorType.userExisted, "用户名已经存在");
        }
        userClients.add(userClient);
    }

    private boolean isUserExisted(String username){
        for(UserClient userClient : userClients){
            if(userClient.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * 发送消息给用户
     * @param message 消息主体
     * @param toUsername 对方用户名
     */
    public void sendMessage(String message, String toUsername){
        UserClient toUserClient = getUserClient(toUsername);
        if(toUserClient == null) return;
        toUserClient.sendMessage(message);
    }

    private UserClient getUserClient(String username){
        for(UserClient userClient : userClients){
            if(userClient.getUsername().equals(username)){
                return userClient;
            }
        }
        return null;
    }
}
