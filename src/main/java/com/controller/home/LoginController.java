package com.controller.home;

import com.server.ContactServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by luyi-netease on 2016/3/18.
 */

@Controller
@RequestMapping("/")
public class LoginController {

    private ContactServer contactServer = ContactServer.getInstance();

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response){
        return "home/login";
    }

    @RequestMapping(value = "contact", method = RequestMethod.POST)
    public String contact(@RequestParam("username") String username, ModelMap map){
        if(StringUtils.isEmpty(username)){
            map.addAttribute("errorMsg", "用户名不能为空");
            return "home/login";
        }
        map.addAttribute("username", username);
        return "home/contact";
    }
}
