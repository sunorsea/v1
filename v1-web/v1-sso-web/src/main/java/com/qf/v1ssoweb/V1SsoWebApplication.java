package com.qf.v1ssoweb;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class V1SsoWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(V1SsoWebApplication.class, args);
    }

}
