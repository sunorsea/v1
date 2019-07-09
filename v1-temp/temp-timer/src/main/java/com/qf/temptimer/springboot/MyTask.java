package com.qf.temptimer.springboot;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/26
 */
@Component
public class MyTask {

    @Scheduled(cron = "* * * * * ?")
    public void doSomething() {
        System.out.println(Thread.currentThread().getName()+"=="+new Date());

    }

}
