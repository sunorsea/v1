package com.qf.tempspringbootrabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/20
 */
@RabbitListener(queues = "spring-simple-queue")
@Component
public class Consumer {

    @RabbitHandler
    public void process(Book book) {
        System.out.println(book.getName());
    }
}
