package com.qf.v1centerweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.qf.v1.api.IProductService;
import com.qf.v1.api.IProductTypeService;
import com.qf.v1.api.ISearchService;
import com.qf.v1.common.constant.RabbitMQConstant;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.common.util.HttpClientUtils;
import com.qf.v1.entity.TProduct;
import com.qf.v1.entity.TProductType;
import com.qf.v1.pojo.TProductVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/11
 */
@Controller
@RequestMapping("product")
public class ProductController {

    @Reference
    private IProductService productService;

    @Reference
    private ISearchService searchService;

    @Reference
    private IProductTypeService productTypeService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("get/{id}")
    @ResponseBody
    public TProduct getById(@PathVariable("id") Long id) {
        return productService.selectByPrimaryKey(id);
    }

//    @RequestMapping("list")
//    public String list(ModelMap model){
//        List<TProduct> list = productService.list();
//        model.put("list", list);
//
//        return "product/list";
//    }

    @RequestMapping("list")
    @ResponseBody
    public List<TProductType> list(ModelMap model) {
        List<TProductType> list = productTypeService.list();
        return list;
    }

    @RequestMapping("page/{pageIndex}/{pageSize}")
    public String page(@PathVariable("pageIndex") Integer pageIndex,
                       @PathVariable("pageSize") Integer pageSize,
                       ModelMap model) {
        PageInfo<TProduct> page = productService.page(pageIndex, pageSize);
        model.put("page", page);
        return "product/list";
    }

    @PostMapping("add")
    public String add(TProductVO vo) {
        Long id = productService.save(vo);
//        searchService.synAllData();
//        searchService.synDataById(id);

//        HttpClientUtils.doGet("http://localhost:9093/item/createHTMLById/" + id);
        rabbitTemplate.convertAndSend(RabbitMQConstant.CENTER_PRODUCT_EXCHANGE,"product.add",id);
        return "redirect:/product/page/1/5";
    }

    @PostMapping("delById/{id}")
    @ResponseBody
    public ResultBean delById(@PathVariable("id") Long id) {
        int count = productService.deleteByPrimaryKey(id);
        if (count > 0) {
            return new ResultBean("200", "删除成功");
        }
        return new ResultBean("404", "删除失败,你懂的!!!");
    }

    @PostMapping("batchDel")
    @ResponseBody
    public ResultBean batchDel(@RequestParam List<Long> ids) {
        Long count = productService.batchDel(ids);
        if (count > 0) {
            return new ResultBean("200", "批量删除成功");
        }
        return new ResultBean("404", "批量删除失败,你懂的!!!");
    }

    @PostMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable("id") Long id,ModelMap modelMap) {
        System.out.println("=======================");
        TProductVO productvo = productService.selectProductById(id);
        System.out.println(productvo.getProduct().getName());
        System.out.println(productvo.getProDesc().getProductName());
        modelMap.put("vo", productvo);
        System.out.println("-----------------------");
        return "product/update";
    }

}
