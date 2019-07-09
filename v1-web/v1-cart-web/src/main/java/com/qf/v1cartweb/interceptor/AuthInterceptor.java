package com.qf.v1cartweb.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v1.api.IUserService;
import com.qf.v1.common.pojo.ResultBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/29
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Reference
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("user_token".equals(cookie.getName())) {
                    String uuid = cookie.getValue();
                    ResultBean resultBean = userService.checkIsLogin(uuid);
                    if ("200".equals(resultBean.getStatusCode())) {
                        request.setAttribute("user",resultBean.getData());
                    }
                }
            }
        }
        return true;
    }
}
