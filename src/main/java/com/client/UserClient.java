package com.client;

import com.exception.ClientErrorType;
import com.exception.ClientException;
import com.google.gson.Gson;
import com.message.DialogueDTO;
import com.server.ContactServer;
import com.user.UserDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Created by luyi-netease on 2016/3/17.
 */
@Slf4j
public class UserClient extends MessageInbound{

    private static ContactServer contactServer = ContactServer.getInstance();

    private static int userId = 0;

    @Getter
    private UserDTO user;

    private WsOutbound wsOutbound;

    public UserClient(){}

    /**
     * 创建客户端
     * @param username 用户名，必须
     */
    public UserClient(String username) throws ClientException{
        if(StringUtils.isEmpty(username)){
            throw ClientException.builder().errorCode(ClientErrorType.systemError).errorMessage("用户名不存在").build();
        }
        user = UserDTO.builder().id(++userId).username(username).build();
    }

    @Override
    /**
     * 打开websocket连接
     */
    public void onOpen(WsOutbound outbound){
        System.out.println("Open Client.");
        wsOutbound = outbound;
        try{
            contactServer.addUserClient(this);
        }
        catch (ClientException e){
            log.info("create Client Exception");
        }
    }

    @Override
    /**
     * 关闭websocket连接
     */
    public void onClose(int status){
        log.info("Client closed");
        contactServer.removeClient(this);
    }

    @Override
    /**
     * 接收文本信息
     */
    public void onTextMessage(CharBuffer cb) throws IOException{
        String message = cb.toString();
        Gson gson = new Gson();
        try{
            DialogueDTO dialogueDTO = gson.fromJson(message, DialogueDTO.class);
            for(UserDTO toUser : dialogueDTO.getToUsers()){
                contactServer.sendMessage(message, toUser);
            }
        }
        catch (Exception e){
            log.error("接收信息有误");
        }
    }

    @Override
    /**
     * 接收二进制数据
     */
    public void onBinaryMessage(ByteBuffer bb) throws IOException{
    }

    /**
     * 发送信息给对应的用户
     * @param message
     */
    public void sendMessage(String message){
        try {
            wsOutbound.writeTextMessage(CharBuffer.wrap(message));
            wsOutbound.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            wsOutbound.close(0, null);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public int getReadTimeout(){
        // 设置websocket的超时时间，单位秒
        return 60000;
    }
}