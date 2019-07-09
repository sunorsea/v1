package com.qf.v1searchweb;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class V1SearchWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(V1SearchWebApplication.class, args);
    }

}
