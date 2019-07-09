package com.qf.temptimer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/26
 */
@Configuration
public class Scheduler implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(getTaskExecutor());
    }
    @Bean
    public Executor getTaskExecutor(){
        return Executors.newScheduledThreadPool(100);
    }
}
