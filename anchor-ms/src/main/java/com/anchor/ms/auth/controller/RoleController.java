package com.anchor.ms.auth.controller;


import com.anchor.core.common.query.QueryPage;
import com.anchor.core.common.dto.Result;
import com.anchor.core.common.dto.ResultGrid;
import com.anchor.core.common.dto.ResultObject;
import com.anchor.ms.auth.model.Role;
import com.anchor.ms.auth.service.IRoleService;
import com.anchor.ms.common.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: RoleController
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    public final static String ROLE_INDEX="role/index";
    public final static String ROLE_ADD_INDEX="role/add";
    public final static String ROLE_EDIT_INDEX="role/edit";

    @Autowired
	private IRoleService roleService;

    /**
     * 首页
     * @return
     */
    @RequestMapping(value="",method = RequestMethod.GET)
    public String index(){
        return ROLE_INDEX;
    }

    @RequestMapping(value="add",method = RequestMethod.GET)
    @ResponseBody
    public String add(){

        return ROLE_ADD_INDEX;
    }
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public Result add(Role role){
        try{
            roleService.insert(role);
        }catch (Exception e){
            new Result().error("添加角色失败：" + e.getMessage());
        }
        return new Result().success("添加角色成功");
    }


    @RequestMapping(value="update/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String edit(){
        return ROLE_EDIT_INDEX;
    }

    @RequestMapping(value="update",method = RequestMethod.POST)
    @ResponseBody
    public Result edit(Role role){
        try{
            roleService.update(role);
        }catch (Exception e){
            new Result().error("修改角色失败：" + e.getMessage());
        }
        return new Result().success("修改角色成功");
    }

    @RequestMapping(value="get/{id}")
    @ResponseBody
    public Result get(@PathVariable("id") long id){
        try{
            return new ResultObject().setData(roleService.get(id));
        }catch (Exception e){
            e.printStackTrace();
            return new Result().error("获取角色失败：" + e.getMessage());
        }
    }

    @RequestMapping(value="list")
    @ResponseBody
    public Result list(){
        try{
            return new ResultObject().setData(roleService.getList());
        }catch (Exception e){
            return new Result().error("获取角色失败：" + e.getMessage());
        }
    }

    @RequestMapping(value="grid")
    @ResponseBody
    public Result grid(QueryPage queryPage){
        try{
            PageInfo<Role> pageInfo = roleService.getPageInfo(queryPage);
            ResultGrid resultGrid = new ResultGrid<Role>();
            resultGrid.setRows(pageInfo.getList());
            resultGrid.setTotal(pageInfo.getTotal());
            return resultGrid;
        }catch (Exception e){
            return new Result().error("获取角色列表失败：" + e.getMessage());
        }
    }


}