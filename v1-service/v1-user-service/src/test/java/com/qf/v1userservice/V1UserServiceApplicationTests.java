package com.qf.v1userservice;

import com.qf.v1.api.IUserService;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.entity.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V1UserServiceApplicationTests {

    @Autowired
    private IUserService userService;

    @Test
    public void contextLoads() {

        TUser user=new TUser();
        user.setUsername("admin");
        user.setPassword("admin11");
        ResultBean resultBean = userService.checkLogin(user);
        System.out.println(resultBean.getStatusCode());
        System.out.println(resultBean.getData());

    }

    @Test
    public void checkIsLogin() {
        ResultBean resultBean = userService.checkIsLogin("e8b8676a-a942-4017-8595-b92a3f5f27e1");
        System.out.println(resultBean.getStatusCode());
        TUser user= (TUser) resultBean.getData();
        if (user != null) {
            System.out.println(user.getUsername());
        }
    }

    @Test
    public void logout() {
        ResultBean logout = userService.logout("0bfdf438-7748-4e35-8fb9-5bf4050163f9");
        System.out.println(logout.getStatusCode());
    }

}
