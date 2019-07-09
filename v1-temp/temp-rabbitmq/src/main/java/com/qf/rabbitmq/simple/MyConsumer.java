package com.qf.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/20
 */
public class MyConsumer {

    private static String queue_name = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.36.147.81");
        factory.setPort(5672);
        factory.setVirtualHost("/rhb");
        factory.setUsername("rhb");
        factory.setPassword("rhb");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println("接收到消息: "+msg);
            }
        };
        channel.basicConsume(queue_name, true, consumer);
    }
}
