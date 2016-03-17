package com.message;

import java.util.List;

/**
 * Created by luyi-netease on 2016/3/17.
 * 消息主体
 */
public class MessageDTO {

    /**
     * 发送方的username
     */
    private String fromUser;

    /**
     * 接收方的usernames
     */
    private List<String> toUsers;

    /**
     * 消息内容
     */
    private String message;
}
