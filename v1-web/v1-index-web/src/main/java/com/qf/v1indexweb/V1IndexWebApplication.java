package com.qf.v1indexweb;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class V1IndexWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(V1IndexWebApplication.class, args);
    }

}
