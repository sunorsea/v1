package com.qf.v1ssoweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.v1.api.IUserService;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.entity.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/27
 */
@Controller
@RequestMapping("sso")
public class SsoController {

    @Reference
    private IUserService userService;

    @RequestMapping("showLogin")
    public String showLogin(HttpServletRequest request, Model model) {
        String referer = request.getHeader("Referer");
        model.addAttribute("referer", referer);
        return "index";
    }

    //返回页面的方式(form)或者返回resultbean的方式(ajax)
    @RequestMapping("checkLogin")
    public String checkLogin(TUser user,
                             String referer,
                             HttpServletResponse response) {
        ResultBean resultBean = userService.checkLogin(user);
        if ("200".equals(resultBean.getStatusCode())) {
            Cookie cookie = new Cookie("user_token",resultBean.getData().toString());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setDomain("qf.com");
            response.addCookie(cookie);
            if (referer == null || "".equals(referer)) {
                return "redirect:http://www.qf.com:9091/index/home";
            }
            return "redirect:" + referer;
        }
        return "index";
    }

    @RequestMapping("checkLogin2")
    @ResponseBody
    public ResultBean checkLogin2(TUser user, HttpServletResponse response) {
        ResultBean resultBean = userService.checkLogin(user);
        if ("200".equals(resultBean.getStatusCode())) {
            Cookie cookie = new Cookie("user_token",resultBean.getData().toString());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return new ResultBean("200", resultBean.getData());
        }
        return new ResultBean("400", "");
    }

    @RequestMapping("checkIsLogin")
    @CrossOrigin(origins = "*",allowCredentials = "true")
    @ResponseBody
    public ResultBean checkIsLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user_token".equals(cookie.getName())) {
                    String uuid = cookie.getValue();
                    return userService.checkIsLogin(uuid);
                }
            }
        }
        return new ResultBean("404", null);
    }

    @RequestMapping("checkIsLoginJsonp")
    @ResponseBody
    public String checkIsLoginJsonp(@CookieValue(name = "user_token",required = false)String uuid,
                                    String callback) throws JsonProcessingException {
        ResultBean resultBean = null;
        if (uuid != null) {
            resultBean = userService.checkIsLogin(uuid);
        }else {
            resultBean = new ResultBean("404", null);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultBean);
        return callback + "("+json+")";
    }

    @RequestMapping("checkIsLogin2")
    @ResponseBody
    public ResultBean checkIsLogin2(@CookieValue(name = "user_token",required = false)String uuid) {
        if (uuid != null) {
            return userService.checkIsLogin(uuid);
        }
        return new ResultBean("404", null);
    }

    @RequestMapping("logout")
    @ResponseBody
    public ResultBean logout(@CookieValue(name = "user_token",required = false)String uuid,
                             HttpServletResponse response) {
        if (uuid != null) {
            Cookie cookie = new Cookie("user_token", uuid);
            cookie.setPath("/");
            cookie.setDomain("qf.com");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return new ResultBean("200", "注销成功");
//            return userService.logout(uuid);redis的注销方式
        }
        return new ResultBean("404", "注销失败");
    }

}
