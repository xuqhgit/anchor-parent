package com.anchor.ms.auth.controller;
/**
 * Created by apple on 2017/5/16.
 */


import com.anchor.ms.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author apple
 * @ClassName: index
 * @Description:
 * @date 2017/5/16 22:54
 * @since version 1.0
 */
@Controller
@RequestMapping("index")
public class IndexController extends BaseController {
    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "index";
    }


}
