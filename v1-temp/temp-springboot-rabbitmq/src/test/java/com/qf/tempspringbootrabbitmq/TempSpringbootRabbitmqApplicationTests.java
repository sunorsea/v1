package com.qf.tempspringbootrabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TempSpringbootRabbitmqApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {

//        rabbitTemplate.convertAndSend("","spring-simple-queue","spring整合rabbitmq");
//        rabbitTemplate.convertAndSend("fanout_exchange","","gooding");

        Book book = new Book(1, "gggg", 111);
        rabbitTemplate.convertAndSend("","spring-simple-queue",book);
    }


}
