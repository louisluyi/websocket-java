package com.websocket;

import javax.servlet.http.HttpServletRequest;

import com.client.UserClient;
import com.exception.ClientException;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

public class WebSocket extends WebSocketServlet{

    private static final long serialVersionUID = -4853540828121130946L;

    protected StreamInbound createWebSocketInbound(String arg0 , HttpServletRequest arg1) throws ClientException{
        String name = arg1.getParameter("username");
        return new UserClient(name);
    }

    /**
     * 仅仅为了实现这个方法，无实际意义
     * @param subProtocol
     * @return
     */
    protected  StreamInbound createWebSocketInbound(String subProtocol){
        try {
            return new UserClient("louis");
        }
        catch (ClientException e){
            return null;
        }
    }
}
