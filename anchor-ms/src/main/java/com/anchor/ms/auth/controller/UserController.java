package com.anchor.ms.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.anchor.core.common.dto.Result;
import com.anchor.core.common.dto.ResultGrid;
import com.anchor.core.common.dto.ResultObject;
import com.anchor.core.common.query.QueryPage;
import com.anchor.ms.auth.dto.UserDto;
import com.anchor.ms.auth.model.User;
import com.anchor.ms.auth.service.IUserService;
import com.anchor.ms.common.controller.BaseController;
import com.anchor.ms.common.utils.EncoderUtil;
import com.anchor.ms.common.utils.LoggerUtils;
import com.anchor.ms.common.utils.StringUtils;
import com.anchor.ms.core.shiro.token.manager.TokenManager;
import com.anchor.ms.core.statics.Constant;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    public String add(){

        return PATH_ADD_INDEX;
    }
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public Result add(@Validated User user, Errors errors){
        try{
            if(errors.hasErrors()){
                return new Result().error("添加失败：" + errors.getFieldError().getDefaultMessage());
            }
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setPassword(EncoderUtil.EncoderByMd5(Constant.DEAFULT_PASSWORD));
            userService.insert(user);
        }catch (Exception e){
            e.printStackTrace();
            return new Result().error("添加失败：" + e.getMessage());
        }
        return new Result().success("添加成功");
    }


    @RequestMapping(value="edit/{id}",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PATH_EDIT_INDEX);
        modelAndView.getModelMap().put("user",userService.get(id));
        return modelAndView;
    }

    @RequestMapping(value="edit",method = RequestMethod.POST)
    @ResponseBody
    public Result edit(User user){
        try{
            userService.update(user);
        }catch (Exception e){
            return new Result().error("修改失败：" + e.getMessage());
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
    public Result grid(QueryPage<User> queryPage,User user){
        try{
            queryPage.setT(user);
            PageInfo<UserDto> pageInfo = userService.getPageInfo(queryPage);
            ResultGrid resultGrid = new ResultGrid<User>();
            resultGrid.setRows(pageInfo.getList());
            resultGrid.setTotal(pageInfo.getTotal());
            return resultGrid;
        }catch (Exception e){
            e.printStackTrace();
            return new Result().error("获取列表失败：" + e.getMessage());
        }
    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request){

        User user = TokenManager.getToken();
        System.out.println(JSONObject.toJSONString(user));
        return "login";
    }
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(){
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(User user,Boolean rememberMe,HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();
        try {
            user = TokenManager.login(user,true);
            resultMap.put("status", 200);
            resultMap.put("message", "登录成功");
            /**
             * shiro 获取登录之前的地址
             * 之前0.1版本这个没判断空。
             */
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            String url = null ;
            if(null != savedRequest){
                url = savedRequest.getRequestUrl();
            }
            /**
             * 我们平常用的获取上一个请求的方式，在Session不一致的情况下是获取不到的
             * String url = (String) request.getAttribute(WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE);
             */
            LoggerUtils.fmtDebug(getClass(), "获取登录之前的URL:[%s]",url);
            //如果登录之前没有地址，那么就跳转到首页。
            if(StringUtils.isBlank(url)){

//                url = request.getContextPath() + "/user/index.shtml";
            }
            modelAndView.setViewName("redirect:/index");
            //跳转地址
//            resultMap.put("back_url", url);
            /**
             * 这里其实可以直接catch Exception，然后抛出 message即可，但是最好还是各种明细catch 好点。。
             */
            return modelAndView;
        } catch (DisabledAccountException e) {
            modelAndView.addObject("errorMsg","帐号已经禁用");
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("errorMsg","帐号或密码错误");
        }

        modelAndView.setViewName("login");

        return modelAndView;
    }
}

