package com.qf.jedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:redis.xml")
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void stringTest() {
        redisTemplate.setKeySerializer(new JdkSerializationRedisSerializer());
        redisTemplate.opsForValue().set("target4","ooooo");
        System.out.println(redisTemplate.opsForValue().get("target4"));
    }

    @Test
    public void noBatchTest() {
        long start=System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            redisTemplate.opsForValue().set("k"+i,"v"+i);
        }
        long end=System.currentTimeMillis();
        System.out.println(end-start);//2892
    }

    @Test
    public void batchTest() {
        long start=System.currentTimeMillis();
        redisTemplate.executePipelined(new SessionCallback() {
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (int i = 10000; i < 20000; i++) {
                    operations.opsForValue().set("k"+i,"v"+i);
                }
                return null;
            }
        });
        long end=System.currentTimeMillis();
        System.out.println(end-start);//2892  690
    }

    @Test
    public void ttlTest() {
        redisTemplate.opsForValue().set("love","liyiwen");
        redisTemplate.expire("love",10, TimeUnit.SECONDS);
        Long expire = redisTemplate.getExpire("love", TimeUnit.SECONDS);
        System.out.println(expire);
    }

}
