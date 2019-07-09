package com.qf.tempspringbootredis;

import com.qf.tempspringbootredis.config.GoogleBloomFilter;
import com.qf.tempspringbootredis.entity.ProductType;
import com.qf.tempspringbootredis.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TempSpringbootRedisApplicationTests {

    @Resource(name = "redisTemplate2")
    private RedisTemplate redisTemplate;

    @Autowired
    private GoogleBloomFilter googleBloomFilter;






    @Test
    public void contextLoads() {
        /*redisTemplate.opsForValue().set("target","springboot整合redis");
        System.out.println(redisTemplate.opsForValue().get("target"));*/

        redisTemplate.opsForValue().set("student",new Student(1l,"ggg"));
        Object student = redisTemplate.opsForValue().get("student");
        System.out.println(student);
    }

    @Test
    public void cacheTest() {
        String key="product:types";
        List<ProductType> types = (List<ProductType>) redisTemplate.opsForValue().get(key);
        if (types == null) {
            String uuid = UUID.randomUUID().toString();
           // Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid);

            Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 60, TimeUnit.SECONDS);

            if (lock) {
               // redisTemplate.expire("lock", 1, TimeUnit.SECONDS);

                System.out.println("缓存中没有,需要去数据库中查找");
                types = new ArrayList<>();
                types.add(new ProductType(1, "电子数码"));
                types.add(new ProductType(2, "有钱真好"));
                redisTemplate.opsForValue().set(key, types);
                System.out.println("放入缓存成功");

                String currentUuid  = (String) redisTemplate.opsForValue().get("lock");
                if (currentUuid.equals(uuid)) {
                    redisTemplate.delete("lock");
                }
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cacheTest();
            }
        } else {
            System.out.println("从缓存中获取到数据");
            for (ProductType type : types) {
                System.out.println(type.getName());
            }
        }
    }

    @Test
    public void multiThreadTest() throws InterruptedException {
        ExecutorService pool = new ThreadPoolExecutor(
                10, 20, 100, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(100));
        for (int i = 0; i < 100; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    cacheTest();
                }
            });
        }
        Thread.sleep(10000);
    }

    @Test
    public void lockScriptTest(){
        //1.创建一个可以执行lua脚本的执行对象
        DefaultRedisScript<Boolean> lockScript = new DefaultRedisScript<>();
        //2.获取要执行的脚本
        lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("set.lua")));
        //3.设置返回类型
        lockScript.setResultType(Boolean.class);
        //5.执行脚本
        Boolean result = (Boolean) redisTemplate.execute(lockScript, null);
        System.out.println(result);

    }

    @Test
    public void lockScriptTest2(){
        //1.创建一个可以执行lua脚本的执行对象
        DefaultRedisScript<Boolean> lockScript = new DefaultRedisScript<>();
        //2.获取要执行的脚本
        lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lock.lua")));
        //3.设置返回类型
        lockScript.setResultType(Boolean.class);
        //5.执行脚本
        List<String> params=new ArrayList<>(3);
        params.add("lock");
        params.add(UUID.randomUUID().toString());
        params.add("60");

        Boolean result = (Boolean) redisTemplate.execute(lockScript, params);
        if (result) {
            Long expire = redisTemplate.getExpire("lock");
            System.out.println(expire);
        } else {
            System.out.println(result);
        }
    }

    @Test
    public void cachePenetrateTest() {
        String key = "1";
        Student student  = (Student) redisTemplate.opsForValue().get(key);
        if (student == null) {
            System.out.println("查询数据库");
            redisTemplate.opsForValue().set(key, new Student());
            redisTemplate.expire(key, 60, TimeUnit.SECONDS);
        } else {
            System.out.println("从缓存中获取到该对象");
            System.out.println(student);
        }
    }

    @Test
    public void goolgeFilterTest() {
        List<Boolean> results = new ArrayList<>();
        for (long i = 1000; i <= 3000; i++) {
            boolean exists = googleBloomFilter.isExists(i);
            if (exists) {
                results.add(exists);
            }
        }
        System.out.println(results.size());
    }

    @Test
    public void redisBloomTest() {
        System.out.println(googleBloomFilter);

    }


}
