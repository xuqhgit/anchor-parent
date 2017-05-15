package com.anchor.ms.auth.controller;


import com.anchor.core.common.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anchor.ms.auth.service.IDictService;

/**
 * @ClassName: DictController
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
@Controller
@RequestMapping("dict")
public class DictController extends BaseController {

    @Autowired
	private IDictService dictService;

}