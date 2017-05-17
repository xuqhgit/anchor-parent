package com.anchor.ms.auth.controller;


import com.anchor.core.common.base.BaseController;
import com.anchor.ms.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anchor.ms.auth.service.IUserService;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: UserController
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
	private IUserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){

        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView login(String username,String password){
        userService.findUserByUsername(username);
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByUsername(username);
        if(user!=null&&user.getPassword().equals(password)){
            modelAndView.setViewName("redirect:/index");
            user.setPassword(null);
//

            return modelAndView;
        }
        modelAndView.setViewName("login");
        modelAndView.addObject("error","用户名或者密码错误");

        return modelAndView;
    }
}