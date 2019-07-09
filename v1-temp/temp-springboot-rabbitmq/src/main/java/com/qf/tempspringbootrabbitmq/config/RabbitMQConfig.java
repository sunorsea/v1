package com.qf.tempspringbootrabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/20
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue initQueue() {
        return new Queue("spring-simple-queue", false, false, false);
    }
}
