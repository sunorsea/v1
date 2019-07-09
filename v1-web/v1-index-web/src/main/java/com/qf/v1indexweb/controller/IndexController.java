package com.qf.v1indexweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v1.api.IProductTypeService;
import com.qf.v1.entity.TProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/14
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @Reference
    private IProductTypeService productTypeService;

    @RequestMapping("home")
    public String showHome(ModelMap model) {
        List<TProductType> list = productTypeService.list();
        model.put("list", list);
        return "home";
    }

    @RequestMapping("list")
    @ResponseBody
    public List<TProductType> list() {
        return productTypeService.list();
    }

}
