package com.qf.v1registerweb;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class V1RegisterWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(V1RegisterWebApplication.class, args);
    }

}
