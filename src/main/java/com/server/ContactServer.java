package com.server;

import com.exception.ClientErrorType;
import com.exception.ClientException;
import com.client.UserClient;
import com.google.gson.Gson;
import com.message.UserListDTO;
import com.user.UserDTO;
import org.apache.catalina.User;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luyi-netease on 2016/3/17.
 */
public class ContactServer {

    private static List<UserClient> userClients = new ArrayList();

    private static ContactServer contactServer = null;

    private ContactServer(){}

    public static ContactServer getInstance(){
        if(contactServer == null){
            contactServer = new ContactServer();
        }
        return contactServer;
    }

    public void addUserClient(UserClient userClient) throws ClientException{
        if(isUserExisted(userClient.getUser())){
            throw ClientException.builder().errorCode(ClientErrorType.userExisted).errorMessage("用户名已经存在").build();
        }
        sendUserListDTOToNewUser(userClient.getUser());
        sendUserToCurrentClients(userClient.getUser());
        userClients.add(userClient);
    }

    public boolean isUserExisted(UserDTO user){
        for(UserClient userClient : userClients){
            if(userClient.getUser().getUsername().equals(user.getUsername())){
                return true;
            }
        }
        return false;
    }

    /**
     * 把当前用户列表发给新加入的用户
     * @param newUser 新用户
     */
    private void sendUserListDTOToNewUser(UserDTO newUser){
        List<UserDTO> userList = new ArrayList<UserDTO>();
        for(UserClient other : userClients){
            userList.add(other.getUser());
        }
        UserListDTO userListDTO = UserListDTO.builder().userList(userList).build();
        sendMessage(new Gson().toJson(userListDTO), newUser);
    }

    /**
     * 把新用户发给每个当前用户
     * @param newUser
     */
    private void sendUserToCurrentClients(UserDTO newUser){
        List<UserDTO> userList = new ArrayList<UserDTO>();
        userList.add(newUser);
        UserListDTO userListDTO = UserListDTO.builder().userList(userList).build();
        for(UserClient other : userClients){
            sendMessage(new Gson().toJson(userListDTO), other.getUser());
        }
    }

    /**
     * 发送消息给用户
     * @param message 消息主体
     * @param toUser 对方用户
     */
    public void sendMessage(String message, UserDTO toUser){
        UserClient toUserClient = getUserClient(toUser);
        if(toUserClient == null) return;
        toUserClient.sendMessage(message);
    }


    private UserClient getUserClient(UserDTO user){
        for(UserClient userClient : userClients){
            if(userClient.getUser().getUsername().equals(user.getUsername())){
                return userClient;
            }
        }
        return null;
    }

    public void removeClient(UserClient userClient){
        userClients.remove(userClient);
    }
}
