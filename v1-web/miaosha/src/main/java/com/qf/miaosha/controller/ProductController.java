package com.qf.miaosha.controller;

import com.qf.miaosha.entity.TProduct;
import com.qf.miaosha.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/7/8
 */
@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @RequestMapping("getById")
    public String getById(Long id, ModelMap modelMap) {
        TProduct product = productService.getById(id);
        modelMap.put("product", product);
        System.out.println(productService.getClass()+"==================");
        return "item";
    }

    @RequestMapping("sale")
    @ResponseBody
    public String sale(Long id) {
        boolean result = productService.sale(id);
        if (result) {
            return "success";
        }
        return "faild";
    }

}
