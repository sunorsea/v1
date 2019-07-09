package com.qf.v1registerweb.config;

import com.qf.v1.common.constant.RabbitMQConstant;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/25
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange initExchange() {
        return new TopicExchange(RabbitMQConstant.REGISTER_EXCHANGE);
    }
}
