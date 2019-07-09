package com.qf.v1commonservice;

import com.qf.v1.api.IEmaiService;
import com.qf.v1.common.pojo.ResultBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V1CommonServiceApplicationTests {

    @Autowired
    private IEmaiService emaiService;

    @Test
    public void contextLoads() {
        ResultBean resultBean = emaiService.send(
                "r1874012876@163.com", "服务发布", "服务发布测试");
        System.out.println(resultBean.getStatusCode());

    }

    @Test
    public void ggtest() {
        Integer i1 = new Integer(12);
        Integer i2 = new Integer(12);
        System.out.println(i1 == i2);

        Integer i3 = 126;
        Integer i4 = 126;
        int i5 = 126;
        System.out.println(i3 == i4);
        System.out.println(i3 == i5);

        Integer i6 = 128;
        Integer i7 = 128;
        int i8 = 128;
        System.out.println(i6 == i7);
        System.out.println(i6 == i8);
    }

}
