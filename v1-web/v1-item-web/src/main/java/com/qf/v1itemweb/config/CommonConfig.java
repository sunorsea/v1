package com.qf.v1itemweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/20
 */
@Configuration
public class CommonConfig {

    @Bean
    public ThreadPoolExecutor initThreadPoolExecutor() {
        int cpus = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                cpus, cpus*2, 10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100));
        return pool;
    }
}
