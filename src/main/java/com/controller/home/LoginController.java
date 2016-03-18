package com.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by luyi-netease on 2016/3/18.
 */

@Controller
@RequestMapping("/")
public class LoginController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response){
        return "home/login";
    }

    @RequestMapping(value = "contact", method = RequestMethod.POST)
    public String contact(@RequestParam("username") String username, ModelMap map){
        map.addAttribute("username", username);
        return "home/contact";
    }
}
