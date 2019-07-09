package com.qf.v1cartservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.api.ICartService;
import com.qf.pojo.CartItem;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.entity.TProduct;
import com.qf.v1.mapper.TProductMapper;
import com.qf.vo.CartItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/29
 */
@Service
public class CartServiceImpl implements ICartService {

    @Resource(name = "redisTemplate1")
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private TProductMapper productMapper;

    @Override
    public ResultBean add(String uuid, Long productId, Integer count) {
        String key = new StringBuilder("user:cart:").append(uuid).toString();
        CartItem cartItem
                = (CartItem) redisTemplate.opsForHash().get(key, productId.toString());
        if (cartItem != null) {
            cartItem.setCount(cartItem.getCount() + count);
            cartItem.setUpdateTime(new Date());
        } else {
            cartItem = new CartItem(productId,count,new Date());
        }

        redisTemplate.opsForHash().put(key,productId.toString(),cartItem);
        redisTemplate.expire(key, 7, TimeUnit.DAYS);
        return new ResultBean("200","添加到购物车成功");
    }

    @Override
    public ResultBean del(String uuid,Long productId) {
        String key = new StringBuilder("user:cart:").append(uuid).toString();
        Long result = redisTemplate.opsForHash().delete(key, productId.toString());
        if (result > 0) {
            redisTemplate.expire(key, 7, TimeUnit.DAYS);
            return new ResultBean("200", "删除成功");
        }
        return new ResultBean("404","不存在商品,删除失败");
    }

    @Override
    public ResultBean update(String uuid, Long productId, Integer count) {
        String key = new StringBuilder("user:cart:").append(uuid).toString();
        CartItem cartItem  = (CartItem) redisTemplate.opsForHash().get(key, productId.toString());
        if (cartItem != null) {
            cartItem.setCount(count);
            cartItem.setUpdateTime(new Date());
            redisTemplate.opsForHash().put(key,productId.toString(),cartItem);
            redisTemplate.expire(key, 7, TimeUnit.DAYS);
            return new ResultBean("200", "购物车商品数量更新成功");
        }
        return new ResultBean("404", "购物车不存在该商品更新失败");
    }

    @Override
    public ResultBean query(String uuid) {
        String key = new StringBuilder("user:cart:").append(uuid).toString();
        Map<Object, Object> cartMap = redisTemplate.opsForHash().entries(key);
        TreeSet<CartItemVO> cart = new TreeSet<>();
        Set<Map.Entry<Object, Object>> entries = cartMap.entrySet();
        List<Long> ids = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : entries) {
            CartItem cartItem = (CartItem) entry.getValue();
            CartItemVO cartItemVO = new CartItemVO();
            cartItemVO.setCount(cartItem.getCount());
            cartItemVO.setUpdateTime(cartItem.getUpdateTime());
            //没优化
            cartItemVO.setProduct(productMapper.selectByPrimaryKey(cartItem.getProductId()));

            //优化day19-08
            /*String productKey = new StringBuilder("product:").append(cartItem.getProductId()).toString();
            TProduct product = (TProduct) redisTemplate.opsForValue().get(productKey);
            if (product != null) {
                cartItemVO.setProduct(product);
            } else {
                ids.add(cartItem.getProductId());
            }*/

            cart.add(cartItemVO);
        }

        /*if (!ids.isEmpty()) {
            List<TProduct> list = null;
        }*/

        redisTemplate.expire(key, 7, TimeUnit.DAYS);
        return new ResultBean("200",cart);
    }


    public ResultBean getCart(String uuid) {
        String key = new StringBuilder("user:cart:").append(uuid).toString();
        Map<Object, Object> cartMap = redisTemplate.opsForHash().entries(key);
        TreeSet<CartItem> cart = new TreeSet<>();
        Set<Map.Entry<Object, Object>> entries = cartMap.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            CartItem cartItem = (CartItem) entry.getValue();
            cart.add(cartItem);
        }
        redisTemplate.expire(key, 7, TimeUnit.DAYS);
        return new ResultBean("200",cart);
    }
}
