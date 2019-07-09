package com.qf.v1cartweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.api.ICartService;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.entity.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/29
 */
@Controller
@RequestMapping("cart")
public class CartController {

    @Reference
    private ICartService cartService;

    @RequestMapping("add/{productId}/{count}")
    @ResponseBody
    public ResultBean add(@PathVariable("productId")Long productId,
                          @PathVariable("count")Integer count,
                          @CookieValue(name = "user_cart",required = false)String uuid,
                          HttpServletResponse response,
                          HttpServletRequest request) {

        TUser currentUser  = (TUser) request.getAttribute("user");
        if (currentUser!=null) {
            uuid = currentUser.getId().toString();
            ResultBean resultBean = cartService.add(uuid, productId, count);
            return resultBean;
        }
        if (uuid == null || "".equals(uuid)) {
            uuid = UUID.randomUUID().toString();
        }

        ResultBean resultBean = cartService.add(uuid, productId, count);
        if ("200".equals(resultBean.getStatusCode())) {
            reflushCookie(uuid, response);
        }
        return resultBean;
    }

    @RequestMapping("query")
    @ResponseBody
    public ResultBean query(@CookieValue(name = "user_cart",required = false)String uuid,
                            HttpServletResponse response,
                            HttpServletRequest request) {
        TUser currentUser  = (TUser) request.getAttribute("user");
        if (currentUser!=null) {
            uuid = currentUser.getId().toString();
            ResultBean resultBean = cartService.query(uuid);
            return resultBean;
        }
        if (uuid == null || "".equals(uuid)) {
            return new ResultBean("404", "购物车为空");
        }

        ResultBean resultBean = cartService.query(uuid);
        if ("200".equals(resultBean.getStatusCode())) {
            reflushCookie(uuid, response);
        }

        return resultBean;
    }

    @RequestMapping("del/{productId}")
    @ResponseBody
    public ResultBean del(@CookieValue(name = "user_cart", required = false) String uuid,
                          @PathVariable("productId") Long productId,
                          HttpServletResponse response) {
        if (uuid == null || "".equals(uuid)) {
            return new ResultBean("404", "购物车为空");
        }
        ResultBean resultBean = cartService.del(uuid, productId);
        if ("200".equals(resultBean.getStatusCode())) {
            reflushCookie(uuid, response);
        }
        return resultBean;
    }

    @RequestMapping("update/{productId}/{count}")
    @ResponseBody
    public ResultBean update(@CookieValue(name = "user_cart",required = false)String uuid,
                             @PathVariable("productId")Long productId,
                             @PathVariable("count")Integer count,
                             HttpServletResponse response) {
        if (uuid == null || "".equals(uuid)) {
            return new ResultBean("404", "购物车为空");
        }
        ResultBean resultBean = cartService.update(uuid, productId, count);
        if ("200".equals(resultBean.getStatusCode())) {
            reflushCookie(uuid, response);
        }
        return resultBean;
    }

    private void reflushCookie(@CookieValue(name = "user_cart", required = false) String uuid, HttpServletResponse response) {
        Cookie cookie = new Cookie("user_cart", uuid);
        cookie.setPath("/");
        cookie.setDomain("qf.com");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }

}
