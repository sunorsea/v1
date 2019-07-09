package com.qf.v1cartweb;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class V1CartWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(V1CartWebApplication.class, args);
    }

}
