package com.qf.miaosha.scheduling;

import com.qf.miaosha.entity.TSeckill;
import com.qf.miaosha.mapper.TSeckillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/7/8
 */
@Component
public class SeckillTask {

    @Autowired
    private TSeckillMapper seckillMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0/10 * * * * *")
    public void startSeckill() {
//        System.out.println("开始扫描秒杀活动表,根据时间开启符合条件的秒杀活动");
        List<TSeckill> seckills = seckillMapper.getCanStartSeckill();
        if (seckills != null && !seckills.isEmpty()) {
            for (TSeckill seckill : seckills) {
                seckill.setStatus("1");
                seckillMapper.updateByPrimaryKeySelective(seckill);
                System.out.println("秒杀活动:"+seckill.getId()+"===已经开启");
                String key = new StringBuilder("seckill:product:").append(seckill.getId()).toString();
                Integer count = seckill.getCount();
                for (Integer i = 0; i < count; i++) {
                    redisTemplate.opsForList().leftPush(key, seckill.getId());
                }
                System.out.println("redis秒杀队列准备就绪");

                String seckillKey = new StringBuilder("seckill:").append(seckill.getId()).toString();
                redisTemplate.opsForValue().set(seckillKey,seckill);
            }
        }
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void stopSeckill() {
        System.out.println("开始扫描秒杀活动表,根据时间关闭符合条件的秒杀活动");
        List<TSeckill> seckills = seckillMapper.getCanStopSeckill();
        if (seckills != null && !seckills.isEmpty()) {
            for (TSeckill seckill : seckills) {
                seckill.setStatus("2");
                seckillMapper.updateByPrimaryKeySelective(seckill);
                System.out.println("秒杀活动: "+ seckill.getId()+"==已经结束");

                String key = new StringBuilder("seckill:product:").append(seckill.getId()).toString();
                redisTemplate.delete(key);
                System.out.println("清除掉秒杀队列");
            }
        }
    }

}
