package com.message;

import com.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by luyi-netease on 2016/3/17.
 * 消息主体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogueDTO {

    /**
     * 类型
     */
    private int type = MessageType.DIALOGUE;
    /**
     * 发送方的user
     */
    private UserDTO fromUser;

    /**
     * 接收方的users
     */
    private List<UserDTO> toUsers;

    /**
     * 消息内容
     */
    private String message;

}
