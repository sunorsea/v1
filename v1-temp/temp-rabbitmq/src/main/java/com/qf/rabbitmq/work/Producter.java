package com.qf.rabbitmq.work;

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

    private static String queue_name = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.36.147.81");
        factory.setPort(5672);
        factory.setVirtualHost("/rhb");
        factory.setUsername("rhb");
        factory.setPassword("rhb");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue_name, false, false, false, null);
        for (int i = 1; i <= 10; i++) {
            String msg="人生就应该做点有意义的事"+i;
            channel.basicPublish("",queue_name,null,msg.getBytes());
        }
        System.out.println("发送消息成功");
    }
}
