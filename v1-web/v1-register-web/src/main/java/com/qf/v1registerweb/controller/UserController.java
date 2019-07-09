package com.qf.v1registerweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v1.api.IUserService;
import com.qf.v1.common.constant.RabbitMQConstant;
import com.qf.v1.entity.TUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/25
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Reference
    private IUserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TemplateEngine templateEngine;

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("register1")
    public String register1() {

        return "register1";
    }

    @RequestMapping("register2")
    public String register2() {

        return "register2";
    }

    @PostMapping("add")
    public String add(TUser user) {
        int id = userService.insertSelective(user);
        if (id > 0) {
            Context context = new Context();
            String uuid = UUID.randomUUID().toString();
            String href = new StringBuilder("http://localhost:9094/user/active/").append(uuid).toString();
            context.setVariable("username",user.getUsername());
            context.setVariable("href", href);
            String text = templateEngine.process("jihuo.html", context);
            redisTemplate.opsForValue().set(uuid,id);

            Map<String, String> map = new HashMap<>();
            map.put("to", user.getEmail());
            map.put("subject", "疯狂购物商城激活邮件");
            map.put("text",text);
            rabbitTemplate.convertAndSend(RabbitMQConstant.REGISTER_EXCHANGE,"user.add",map);
            return "success";
        }
        return "register2";
    }

}
