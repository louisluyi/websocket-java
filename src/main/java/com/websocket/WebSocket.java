package com.websocket;

import javax.servlet.http.HttpServletRequest;

import com.client.UserClient;
import com.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class WebSocket extends WebSocketServlet{

    private static final long serialVersionUID = -4853540828121130946L;

    protected StreamInbound createWebSocketInbound(String arg0, HttpServletRequest request) throws ClientException{
        String username = getParameter(request.getQueryString(), "username");
        return new UserClient(username);
    }

    private String getParameter(String queryString, String name){
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(queryString)) return null;
        String[] arr = queryString.split("&");
        try{
            for(String s : arr){
                String[] pair = s.split("=");
                if(pair.length == 2 && pair[0].equals(name)){
                    return URLDecoder.decode(pair[1], "UTF-8").toString();
                }
            }
        }
        catch (IOException e){
            log.error("转码失败");
            return null;
        }
        return null;
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
