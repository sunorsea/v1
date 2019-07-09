package com.qf.v1cartservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.qf.v1.mapper")
public class V1CartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(V1CartServiceApplication.class, args);
    }

}
