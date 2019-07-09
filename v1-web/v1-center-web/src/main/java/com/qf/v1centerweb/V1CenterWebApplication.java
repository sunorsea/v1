package com.qf.v1centerweb;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Import(FdfsClientConfig.class)
@EnableDubbo
public class V1CenterWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(V1CenterWebApplication.class, args);
    }

}
