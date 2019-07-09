package com.qf.tempspringbootredis.config;

import com.qf.tempspringbootredis.entity.ProductType;
import com.qf.tempspringbootredis.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/7/9
 */
@Component
public class Config {




        @Bean
        public Student country() {
            return new Student();
        }

        @Bean
        public ProductType userInfo() {
            return new ProductType(country());
        }



}
