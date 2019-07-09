package com.qf.v1itemweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v1.api.IProductService;
import com.qf.v1.common.pojo.ResultBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/18
 */
@Controller
@RequestMapping("item")
public class ItemController {

    @Reference
    private IProductService productService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private ThreadPoolExecutor threadPool;

    @RequestMapping("createHTMLById/{id}")
    @ResponseBody
    private ResultBean createHTMLById(@PathVariable("id") Long id) {
        return createHTMLByID(id);
    }

    @RequestMapping("param")
    @ResponseBody
    public ResultBean param(String username,String password) {
        System.out.println(username+"====="+password);
        return new ResultBean("200", "ok");
    }

    private ResultBean createHTMLByID(@PathVariable("id") Long id) {
        try {
            Template template = configuration.getTemplate("item.ftl");
            Map<String, Object> data = new HashMap<>();
            data.put("product", productService.selectByPrimaryKey(id));
            String serverpath = ResourceUtils.getURL("classpath:static").getPath();
            String filePath = new StringBuilder(serverpath).append(File.separator).append(id).append(".html").toString();
            FileWriter writer = new FileWriter(filePath);

            template.process(data, writer);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResultBean("404", "获取模板失败");
        } catch (TemplateException e) {
            e.printStackTrace();
            return new ResultBean("404", "生成静态页面失败");
        }
        return new ResultBean("200", "生成静态页面成功");
    }

    @RequestMapping("batchCreateHTML")
    @ResponseBody
    public ResultBean batchCreateHTML(@RequestParam List<Long> ids) throws ExecutionException, InterruptedException {
        List<Future<Long>> results = new ArrayList<>(ids.size());
        for (Long id : ids) {
//            createHTMLById(id);
            results.add(threadPool.submit(new CreateHTMLTask(id)));
        }

        List<Long> errors=new ArrayList<>();
        for (Future<Long> result : results) {
            Long id = result.get();
            if (id != 0) {
                errors.add(id);
            }
        }
        if (errors.size() > 0) {
            return new ResultBean("500", "批量生成页面失败");
        }
        return new ResultBean("200","批量生成页面成功");
    }

    private class CreateHTMLTask implements Callable<Long>{

        private Long id;

        public CreateHTMLTask(Long id){
            this.id=id;
        }

        @Override
        public Long call() throws Exception {
            try {
                Template template = configuration.getTemplate("item.ftl");
                Map<String, Object> data = new HashMap<>();
                data.put("product", productService.selectByPrimaryKey(id));
                String serverpath = ResourceUtils.getURL("classpath:static").getPath();
                String filePath = new StringBuilder(serverpath).append(File.separator).append(id).append(".html").toString();
                FileWriter writer = new FileWriter(filePath);

                template.process(data, writer);

            } catch (IOException e) {
                e.printStackTrace();
                return id;
            } catch (TemplateException e) {
                e.printStackTrace();
                return id;
            }
            return 0L;
        }
    }
}
