package com.qf.temptimer.jdk;

import java.util.Date;
import java.util.TimerTask;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/26
 */
public class MyTimerTask extends TimerTask {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"=="+new Date());
    }
}
