package com.qf.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/24
 */
public class JedisTest {

    @Test
    public void stringTest() {
        Jedis jedis = new Jedis("10.36.147.81", 6379);
        jedis.auth("ronghaibo");

        /*jedis.set("target", "javaandjedis");
        String target = jedis.get("target");
        System.out.println(target);*/

        jedis.mset("k1", "v1", "k2", "v2");
        List<String> values = jedis.mget("k1", "k2");
        for (String value : values) {
            System.out.println(value);
        }
    }

    @Test
    public void otherTest() {
        Jedis jedis = new Jedis("10.36.147.81", 6379);
        jedis.auth("ronghaibo");

        Map<String,String> map =new HashMap<String, String>();
        map.put("name", "学会生存");
        map.put("price", "6666");
        jedis.hmset("book:1", map);
        Map<String, String> resultMap = jedis.hgetAll("book:1");
        Set<Map.Entry<String, String>> entries = resultMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }


}
