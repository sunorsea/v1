package com.qf.v1itemweb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class V1ItemWebApplicationTests {

    @Autowired
    private Configuration configuration;

    @Test
    public void createHTMLTest() throws IOException, TemplateException {

        Template template = configuration.getTemplate("hello.ftl");
        Map<String, Object> data = new HashMap<>();
        data.put("username", "java1902");

        Student student = new Student(1, "zhangsaan", new Date());
        data.put("student", student);

        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "zhangsaan", new Date()));
        list.add(new Student(2, "lisi", new Date()));
        list.add(new Student(3, "wangba", new Date()));
        data.put("list", list);

        data.put("money", 100000);

        data.put("msg", null);

        FileWriter out = new FileWriter("D:\\ideaworkspace\\v1\\v1-web\\v1-item-web\\src\\main\\resources\\static\\hello.html");
        template.process(data,out);
        System.out.println("生成静态页面成功");

    }

}
