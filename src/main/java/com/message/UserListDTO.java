package com.message;

import com.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luyi-netease on 2016/3/21.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserListDTO {

    /**
     * 类型
     */
    private int type = MessageType.USER_LIST;

    /**
     * 用户列表
     */
    private List<UserDTO> userList = new ArrayList<UserDTO>();
}
