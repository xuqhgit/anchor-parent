package com.anchor.ms.auth.controller;

import com.anchor.core.common.base.BaseController;
import com.anchor.core.common.dto.Result;
import com.anchor.core.common.dto.ResultGrid;
import com.anchor.core.common.dto.ResultObject;
import com.anchor.core.common.query.QueryPage;
import com.anchor.ms.auth.model.DictItemTree;
import com.anchor.ms.auth.model.Permission;
import com.anchor.ms.auth.model.PermissionTree;
import com.anchor.ms.core.shiro.token.manager.TokenManager;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.anchor.ms.auth.service.IDictItemService;
import com.anchor.ms.auth.model.DictItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: DictItemController
 * @Description: 字典元素
 * @author xuqh
 * @date 2017-11-13 18:10:30
 * @since version 1.0
 */
@Controller
@RequestMapping("dictItem")
public class DictItemController extends BaseController{

    @Autowired
	private IDictItemService dictItemService;

	public final static String PATH_INDEX="auth/dictItem/index";
    public final static String PATH_ADD_INDEX="auth/dictItem/add";
    public final static String PATH_EDIT_INDEX="auth/dictItem/edit";

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
    public Result add(DictItem dictItem){
        try{
            dictItem.setCreatorId(TokenManager.getToken().getId());
            dictItem.setStatus("1");
            dictItemService.insert(dictItem);
        }catch (Exception e){
           return new Result().error("添加失败：" + e.getMessage());
        }
        return new Result().success("添加成功");
    }



    @RequestMapping(value="edit/{id}",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PATH_EDIT_INDEX);
        modelAndView.getModelMap().put("dictItem",dictItemService.get(id));
        return modelAndView;
    }

    @RequestMapping(value="edit",method = RequestMethod.POST)
    @ResponseBody
    public Result edit(DictItem dictItem){
        try{
            dictItemService.update(dictItem);
        }catch (Exception e){
           return new Result().error("修改失败：" + e.getMessage());
        }
        return new Result().success("修改成功");
    }

    @RequestMapping(value="get/{id}")
    @ResponseBody
    public Result get(@PathVariable("id") long id){
        try{
            return new ResultObject().setData(dictItemService.get(id));
        }catch (Exception e){
            e.printStackTrace();
            return new Result().error("获取失败：" + e.getMessage());
        }
    }

    @RequestMapping(value="list")
    @ResponseBody
    public Result list(){
        try{
            return new ResultObject().setData(dictItemService.getList());
        }catch (Exception e){
            return new Result().error("获取失败：" + e.getMessage());
        }
    }

    @RequestMapping(value="grid")
    @ResponseBody
    public Result grid(QueryPage<DictItem> queryFilter,DictItem dictItem){
        try{
            queryFilter.setT(dictItem);
            PageInfo<DictItem> pageInfo = dictItemService.getPageInfo(queryFilter);
            ResultGrid resultGrid = new ResultGrid<DictItem>();
            resultGrid.setRows(pageInfo.getList());
            resultGrid.setTotal(pageInfo.getTotal());
            return resultGrid;
        }catch (Exception e){
            return new Result().error("获取列表失败：" + e.getMessage());
        }
    }

    @RequestMapping(value="treegrid")
    @ResponseBody
    public Result treegrid(QueryPage<DictItem> queryPage, DictItem dictItem){
        try{

            queryPage.setT(dictItem);
            queryPage.setSort("rank");
            queryPage.setSortOrder("asc");
            ResultGrid resultGrid = new ResultGrid<DictItemTree>();
            List<DictItemTree> list = new ArrayList<>();
            if(null!=dictItem.getDictId()){
                list = dictItemService.getDictItemTree(queryPage);
            }
            resultGrid.setRows(list);
            resultGrid.setTotal(list.size());
            return resultGrid;
        }catch (Exception e){
            e.printStackTrace();
            return new Result().error("获取列表失败：" + e.getMessage());
        }
    }

}

