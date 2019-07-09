package com.qf.v1commonservice.consumer;

import com.qf.v1.api.IEmaiService;
import com.qf.v1.common.constant.RabbitMQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/25
 */
@Component
public class Consumer {

    @Autowired
    private IEmaiService emaiService;

    @RabbitListener(queues = RabbitMQConstant.EMAIL_QUEUE)
    @RabbitHandler
    public void process(Map<String,String> map) {
        System.out.println(map.get("to"));
        System.out.println(map.get("subject"));
        System.out.println(map.get("text"));

        emaiService.send(map.get("to"),map.get("subject"),map.get("text"));
    }

}
