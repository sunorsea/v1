package com.qf.v1commonservice.config;

import com.qf.v1.common.constant.RabbitMQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/21
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue initQueue() {
        return new Queue(RabbitMQConstant.EMAIL_QUEUE, true, false, false);
    }

    @Bean
    public TopicExchange initExchange() {
        return new TopicExchange(RabbitMQConstant.REGISTER_EXCHANGE);
    }

    @Bean
    public Binding intiBinding(Queue initQueue,TopicExchange initExchange) {
        return BindingBuilder.bind(initQueue).to(initExchange).with("user.add");
    }

}
