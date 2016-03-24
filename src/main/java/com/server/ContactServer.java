package com.server;

import com.exception.ClientErrorType;
import com.exception.ClientException;
import com.client.UserClient;
import com.google.gson.Gson;
import com.message.MessageType;
import com.message.UserListDTO;
import com.user.UserDTO;
import org.apache.catalina.User;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Collections;
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
        //查找同名用户，清除
        for(UserClient other : userClients){
            if(other.getUser().getUsername().equals(userClient.getUser().getUsername())){
                userClients.remove(other);
            }
        }
        userClients.add(userClient);
        sendUserListDTOToNewUser(userClient.getUser());
        sendUserToCurrentClients(userClient.getUser());
    }

    public boolean isUserExisted(String username){
        for(UserClient userClient : userClients){
            if(userClient.getUser().getUsername().equals(username)){
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
            if(other.getUser().getId() == newUser.getId()) continue;
            userList.add(other.getUser());
        }
        if(CollectionUtils.isEmpty(userList)) return;
        UserListDTO userListDTO = UserListDTO.builder().type(MessageType.USER_LIST).userList(userList).build();
        sendMessage(new Gson().toJson(userListDTO), newUser);
    }

    /**
     * 把新用户发给每个当前用户
     * @param newUser
     */
    private void sendUserToCurrentClients(UserDTO newUser){
        List<UserDTO> userList = new ArrayList<UserDTO>();
        userList.add(newUser);
        UserListDTO userListDTO = UserListDTO.builder().type(MessageType.USER_LIST).userList(userList).build();
        for(UserClient other : userClients){
            if(other.getUser().getId() == newUser.getId()) continue;
            sendMessage(new Gson().toJson(userListDTO), other.getUser());
        }
    }

    /**
     * 发送消息给用户
     * @param message 消息主体
     * @param toUser 对方用户
     */
    public void sendMessage(String message, UserDTO toUser){
        if(StringUtils.isEmpty(message)) return;
        UserClient toUserClient = getUserClient(toUser);
        if(toUserClient == null) return;
        toUserClient.sendMessage(message);
    }

    /**
     * 获取对应用户，依据用户名
     * @param user
     * @return
     */
    private UserClient getUserClient(UserDTO user){
        if(user == null) return null;
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
