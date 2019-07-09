package com.qf.v1searchweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v1.api.ISearchService;
import com.qf.v1.common.pojo.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/17
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @RequestMapping("queryByKeywords")
    public String queryByKeywords(String keywords, ModelMap modelMap) {
        ResultBean resultBean = searchService.queryByKeywords(keywords);
        modelMap.put("result", resultBean);
        return "list";
    }

}
