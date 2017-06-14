package com.anchor.ms.auth.controller;

import com.anchor.core.common.base.BaseController;
import com.anchor.core.common.dto.QueryFilter;
import com.anchor.core.common.dto.Result;
import com.anchor.core.common.dto.ResultGrid;
import com.anchor.core.common.dto.ResultObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anchor.ms.auth.service.IUserService;
import com.anchor.ms.auth.model.User;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: UserController
 * @Description: 
 * @author xuqh
 * @date 2017-05-18 14:36:19
 * @since version 1.0
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController{

    @Autowired
	private IUserService userService;

	public final static String PATH_INDEX="auth/user/user";
    public final static String PATH_ADD_INDEX="auth/user/add";
    public final static String PATH_EDIT_INDEX="auth/user/edit";

     /**
     * @return
     */
    @RequestMapping(value="",method = RequestMethod.GET)
    public String index(){
        return PATH_INDEX;
    }

    @RequestMapping(value="add",method = RequestMethod.GET)
    @ResponseBody
    public String add(){

        return PATH_ADD_INDEX;
    }
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public Result add(User user){
        try{
            userService.insert(user);
        }catch (Exception e){
            new Result().error("添加失败：" + e.getMessage());
        }
        return new Result().success("添加成功");
    }


    @RequestMapping(value="update/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String edit(@PathVariable("id") long id){
        return PATH_EDIT_INDEX;
    }

    @RequestMapping(value="update",method = RequestMethod.POST)
    @ResponseBody
    public Result edit(User user){
        try{
            userService.update(user);
        }catch (Exception e){
            new Result().error("修改失败：" + e.getMessage());
        }
        return new Result().success("修改成功");
    }

    @RequestMapping(value="get/{id}")
    @ResponseBody
    public Result get(@PathVariable("id") long id){
        try{
            return new ResultObject().setData(userService.get(id));
        }catch (Exception e){
            e.printStackTrace();
            return new Result().error("获取失败：" + e.getMessage());
        }
    }

    @RequestMapping(value="list")
    @ResponseBody
    public Result list(){
        try{
            return new ResultObject().setData(userService.getList());
        }catch (Exception e){
            return new Result().error("获取失败：" + e.getMessage());
        }
    }

    @RequestMapping(value="grid")
    @ResponseBody
    public Result grid(QueryFilter queryFilter){
        try{
            PageInfo<User> pageInfo = userService.getPageInfo(queryFilter);
            ResultGrid resultGrid = new ResultGrid<User>();
            resultGrid.setRows(pageInfo.getList());
            resultGrid.setTotal(pageInfo.getTotal());
            return resultGrid;
        }catch (Exception e){
            return new Result().error("获取列表失败：" + e.getMessage());
        }
    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){

        return "login";
    }
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(){
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
            return modelAndView;
        }
        modelAndView.setViewName("login");
        modelAndView.addObject("error","用户名或者密码错误");

        return modelAndView;
    }
}

