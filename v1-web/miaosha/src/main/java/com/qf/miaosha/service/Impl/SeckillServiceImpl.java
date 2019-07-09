package com.qf.miaosha.service.Impl;

import com.qf.miaosha.entity.TSeckill;
import com.qf.miaosha.exception.SeckillException;
import com.qf.miaosha.mapper.TSeckillMapper;
import com.qf.miaosha.service.ISeckillService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/7/9
 */
@Service
public class SeckillServiceImpl implements ISeckillService {

    @Autowired
    private TSeckillMapper seckillMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sale(Long seckillId, Long userId) {
        /*TSeckill seckill = seckillMapper.selectByPrimaryKey(seckillId);
        if ("2".equals(seckill.getStatus())) {
            throw new SeckillException("当前活动已结束,下次不早点来还不卖你哦!!!");
        }
        if ("0".equals(seckill.getStatus())) {
            throw new SeckillException("急啥呢,活动都还没开启呢,多等等吧");
        }*/

        String seckillKey = new StringBuilder("seckill:").append(seckillId).toString();
        TSeckill seckill  = (TSeckill) redisTemplate.opsForValue().get(seckillKey);
        if ("2".equals(seckill.getStatus())) {
            throw new SeckillException("当前活动已结束,下次不早点来还不卖你哦!!!");
        }
        if ("0".equals(seckill.getStatus())) {
            throw new SeckillException("急啥呢,活动都还没开启呢,多等等吧");
        }

        String key = new StringBuilder("seckill:product:").append(seckill.getId()).toString();
        Long productId  = (Long) redisTemplate.opsForList().leftPop(key);
        if (productId == null) {
            throw new SeckillException("很遗憾,此商品已经被抢购完,如你需要请联系我们");
        }
        String luckUserKey = new StringBuilder("seckill:user:").append(seckill.getId()).toString();

        Boolean isExists = redisTemplate.opsForSet().isMember(luckUserKey, userId);
        if (isExists) {
            redisTemplate.opsForList().leftPush(key, productId);
            throw new SeckillException("求求你做个人吧,让其他人也买点吧");
        }

        redisTemplate.opsForSet().add(luckUserKey, userId);
        System.out.println(userId+"===用户抢购成功了");
    }

    @Override
    public String senOrderMsg(Long seckillId, Long userId) {
        Long orderno = redisTemplate.opsForValue().increment("orderno");
        String orderNo=orderno.toString();
        String seckillKey = new StringBuilder("seckill:").append(seckillId).toString();
        TSeckill seckill  = (TSeckill) redisTemplate.opsForValue().get(seckillKey);

        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("productId", seckill.getProductId());
        data.put("seckillId", seckill.getId());
        data.put("count", 1);
        data.put("price", seckill.getSalePrice());
        data.put("orderNo", orderNo);
        rabbitTemplate.convertAndSend("seckill-exchange","",data);
        return orderNo;
    }
}
