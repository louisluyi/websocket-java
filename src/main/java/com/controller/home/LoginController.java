package com.controller.home;

import com.server.ContactServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by luyi-netease on 2016/3/18.
 */

@Slf4j
@Controller
@RequestMapping("/")
public class LoginController {

    private ContactServer contactServer = ContactServer.getInstance();

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(HttpSession session, HttpServletResponse response){
        /*String csp = "default-src 'self'";
        response.setHeader("Content-Security-Policy", csp);
        response.setHeader("X-Content-Security-Policy", csp);*/
        session.removeAttribute("username");
        return "home/login";
    }

    @RequestMapping(value = "login/check", method = RequestMethod.POST)
    public String loginCheck(@RequestParam("username") String username, ModelMap map, HttpSession session){
        if(StringUtils.isEmpty(username)){
            map.addAttribute("errorMsg", "用户名不能为空");
            return "home/login";
        }
        if(contactServer.isUserExisted(username)){
            map.addAttribute("errorMsg", "用户名已经存在");
            return "home/login";
        }
        log.info("{} logins", username);
        session.setAttribute("username", username);
        return "redirect:/contact";
    }


    @RequestMapping(value = "contact", method = RequestMethod.GET)
    public String contact(HttpSession session, ModelMap map, HttpServletResponse response) throws IOException{
        if(StringUtils.isEmpty(session.getAttribute("username"))){
            response.sendRedirect("/login");
            return null;
        }
        String username = session.getAttribute("username").toString();
        map.addAttribute("userName", username);
        return "home/contact";
    }
}
