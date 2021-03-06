package com.anchor.ms.auth.controller;

import com.anchor.core.common.base.BaseController;
import com.anchor.core.common.dto.Result;
import com.anchor.core.common.dto.ResultGrid;
import com.anchor.core.common.dto.ResultObject;
import com.anchor.core.common.query.QueryPage;
import com.anchor.ms.auth.model.Permission;
import com.anchor.ms.auth.model.PermissionTree;
import com.anchor.ms.auth.service.IPermissionService;
import com.anchor.ms.core.shiro.token.manager.TokenManager;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PermissionController
 * @Description:
 * @author xuqh
 * @date 2017-10-26 09:33:23
 * @since version 1.0
 */
@Controller
@RequestMapping("permission")
public class PermissionController extends BaseController{

    @Autowired
    private IPermissionService permissionService;

    public final static String PATH_INDEX="auth/permission/index";
    public final static String PATH_ADD_INDEX="auth/permission/add";
    public final static String PATH_EDIT_INDEX="auth/permission/edit";

    /**
     * @return
     */
    @RequestMapping(value="",method = RequestMethod.GET)
    public String index(){
        return PATH_INDEX;
    }

    @RequestMapping(value="add",method = RequestMethod.GET)
    public ModelAndView add(Long pid){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PATH_ADD_INDEX);
        modelAndView.getModelMap().put("pid",pid);
        return modelAndView;
    }
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public Result add(Permission permission){
        try{
            permission.setCreatorId(TokenManager.getToken().getId());
            permission.setStatus("1");
            permissionService.insert(permission);
        }catch (Exception e){
            return new Result().error("添加失败：" + e.getMessage());
        }
        return new Result().success("添加成功");
    }



    @RequestMapping(value="edit/{id}",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PATH_EDIT_INDEX);
        modelAndView.getModelMap().put("permission",permissionService.get(id));
        return modelAndView;
    }

    @RequestMapping(value="edit",method = RequestMethod.POST)
    @ResponseBody
    public Result edit(Permission permission){
        try{
            permissionService.update(permission);
        }catch (Exception e){
            return new Result().error("修改失败：" + e.getMessage());
        }
        return new Result().success("修改成功");
    }

    @RequestMapping(value="get/{id}")
    @ResponseBody
    public Result get(@PathVariable("id") long id){
        try{
            return new ResultObject().setData(permissionService.get(id));
        }catch (Exception e){
            e.printStackTrace();
            return new Result().error("获取失败：" + e.getMessage());
        }
    }

    @RequestMapping(value="list")
    @ResponseBody
    public Result list(){
        try{
            return new ResultObject().setData(permissionService.getList());
        }catch (Exception e){
            return new Result().error("获取失败：" + e.getMessage());
        }
    }



    @RequestMapping(value="grid")
    @ResponseBody
    public Result grid(QueryPage<Permission> queryFilter,Permission permission){
        try{
            queryFilter.setT(permission);
            PageInfo<Permission> pageInfo = permissionService.getPageInfo(queryFilter);
            ResultGrid resultGrid = new ResultGrid<Permission>();
            resultGrid.setRows(pageInfo.getList());
            resultGrid.setTotal(pageInfo.getTotal());
            return resultGrid;
        }catch (Exception e){
            return new Result().error("获取列表失败：" + e.getMessage());
        }
    }

    @RequestMapping(value="treegrid")
    @ResponseBody
    public Result treegrid(QueryPage<Permission> queryPage,Permission permission){
        try{
            queryPage.setT(permission);
            queryPage.setSort("rank");
            queryPage.setSortOrder("asc");
            List<PermissionTree> list = permissionService.findPermissionTree(queryPage);
            ResultGrid resultGrid = new ResultGrid<Permission>();
            resultGrid.setRows(list);
            resultGrid.setTotal(list.size());
            return resultGrid;
        }catch (Exception e){
            return new Result().error("获取列表失败：" + e.getMessage());
        }
    }

    @RequestMapping(value="tree/{roleId}")
    @ResponseBody
    public Result tree(QueryPage<Map> queryPage,@PathVariable Long roleId){
        try{

            Map<String,Object> map = new HashMap<>();
            queryPage.setT(map);
            queryPage.setSortOrder("asc");
            queryPage.setSort("rank");
            map.put("state","1");
            map.put("roleId",roleId);
            List<PermissionTree> list = permissionService.findPermissionTreeAll(queryPage);
            ResultGrid resultGrid = new ResultGrid<Permission>();
            resultGrid.setRows(list);
            resultGrid.setTotal(list.size());
            return resultGrid;
        }catch (Exception e){
            e.printStackTrace();
            return new Result().error("获取列表失败：" + e.getMessage());
        }
    }

    @RequestMapping(value="menu")
    @ResponseBody
    public Result menu(){
        try{
            return new ResultObject().setData(permissionService.findMenu(TokenManager.getToken().getId()));
        }catch (Exception e){
            e.printStackTrace();
            return new Result().error("获取失败：" + e.getMessage());
        }
    }

}

