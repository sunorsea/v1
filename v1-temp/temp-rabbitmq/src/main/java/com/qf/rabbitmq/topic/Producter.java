package com.qf.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/20
 */
public class Producter {

    private static String exchange_name = "topic_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.36.147.81");
        factory.setPort(5672);
        factory.setVirtualHost("/rhb");
        factory.setUsername("rhb");
        factory.setPassword("rhb");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_name, "topic");
        channel.basicPublish(exchange_name,"nba.news",null,"嘎嘎嘎嘎嘎".getBytes());
        channel.basicPublish(exchange_name,"cba.news.man",null,"又有意义有意义有".getBytes());
        System.out.println("发送消息成功");
    }
}
