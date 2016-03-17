
package com.hgq.websocket;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.client.UserClient;
import com.server.ContactServer;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

public class WebSocket extends WebSocketServlet{

    private static ContactServer contactServer = ContactServer.getInstance();

    private static final long serialVersionUID = -4853540828121130946L;

    private static ArrayList<UserClient> mmiList = new ArrayList<UserClient>();

    protected StreamInbound createWebSocketInbound(String arg0 , HttpServletRequest arg1){
        String name = arg1.getParameter("uname");
        return new UserClient(name);
    }

    /**
     * 仅仅为了实现这个方法，无实际意义
     * @param subProtocol
     * @return
     */
    protected  StreamInbound createWebSocketInbound(String subProtocol){
        return new UserClient("louis");
    }

}
