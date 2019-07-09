package com.qf.temptimer.jdk;

import java.util.Date;
import java.util.Timer;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/26
 */
public class Main {
    public static void main(String[] args) {
        Timer timer = new Timer();
        MyTimerTask task = new MyTimerTask();
        System.out.println("main"+new Date());
//        timer.schedule(task,1000);

        timer.schedule(task,1000,1000);
    }
}
