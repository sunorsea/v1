package com.qf.v1searchweb.config;

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
        return new Queue(RabbitMQConstant.PRODUCT_SEARCH_QUEUE, true, false, false);
    }

    @Bean
    public TopicExchange initExchange() {
        return new TopicExchange(RabbitMQConstant.CENTER_PRODUCT_EXCHANGE);
    }

    @Bean
    public Binding intiBinding(Queue initQueue,TopicExchange initExchange) {
        return BindingBuilder.bind(initQueue).to(initExchange).with("product.add");
    }

}
