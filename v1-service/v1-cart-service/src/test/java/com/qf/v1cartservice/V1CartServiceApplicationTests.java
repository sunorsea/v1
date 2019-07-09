package com.qf.v1cartservice;

import com.qf.api.ICartService;
import com.qf.pojo.CartItem;
import com.qf.v1.common.pojo.ResultBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V1CartServiceApplicationTests {

    @Autowired
    private ICartService cartService;

    @Resource(name = "redisTemplate1")
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void addTest() {
        String uuid = "7494872b-d6d9-4784-8d9a-932d681f3286";
        ResultBean resultBean = cartService.add(uuid, 1L, 10);
        System.out.println(resultBean.getStatusCode());
    }

    @Test
    public void queryTest() {
        ResultBean resultBean = cartService.query("7494872b-d6d9-4784-8d9a-932d681f3286");
        TreeSet<CartItem> cartItems = (TreeSet<CartItem>) resultBean.getData();
        for (CartItem cartItem : cartItems) {
            System.out.println(cartItem);
        }
    }

    @Test
    public void delTest() {
        ResultBean resultBean = cartService.del("7494872b-d6d9-4784-8d9a-932d681f3286", 2L);
        System.out.println(resultBean.getStatusCode());
    }

    @Test
    public void updateTest() {
        ResultBean resultBean = cartService.update("7494872b-d6d9-4784-8d9a-932d681f3286", 1l, 666);
        System.out.println(resultBean.getStatusCode());
    }

}
