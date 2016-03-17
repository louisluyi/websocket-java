package com.client;

import com.exception.ClientException;
import com.message.MessageDTO;
import com.server.ContactServer;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luyi-netease on 2016/3/17.
 */
public class UserClient extends MessageInbound{

    private static ContactServer contactServer = ContactServer.getInstance();

    private String username;
    
    public String getUsername(){
        return username;
    }

    public UserClient(){}

    public UserClient(String username){
        this.username = username;
    }
    
    private WsOutbound wsOutbound;
    
    public WsOutbound getMyoutbound(){
        return wsOutbound;
    } 

    @Override
    /**
     * 打开websocket连接
     */
    public void onOpen(WsOutbound outbound){
        try{
            System.out.println("Open Client.");
            this.wsOutbound = outbound;
            contactServer.addUserClient(this);
            sendMessage("系统消息： " + username + ", 欢迎进入聊天室!");
        }
        catch (ClientException e){
            sendMessage(e.getErrorMessage());
        }
    }

    @Override
    /**
     * 关闭websocket连接
     */
    public void onClose(int status){
        System.out.println("Close Client.");
        //todo remove this client
    }

    @Override
    /**
     * 接收文本信息
     */
    public void onTextMessage(CharBuffer cb) throws IOException{
        String message = cb.toString();
        MessageDTO messageDTO = new MessageDTO();
        //todo serialize the String to messageDTO
    }

    @Override
    /**
     * 接收二进制数据
     */
    public void onBinaryMessage(ByteBuffer bb) throws IOException{
    }

    public void sendMessage(String message){
        try {
            wsOutbound.writeTextMessage(CharBuffer.wrap(message));
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