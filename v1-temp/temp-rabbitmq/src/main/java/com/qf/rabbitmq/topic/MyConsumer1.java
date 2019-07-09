package com.qf.rabbitmq.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/20
 */
public class MyConsumer1 {

    private static String exchange_name = "topic_exchange";
    private static String queue_name="topic_exchange_queue1";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.36.147.81");
        factory.setPort(5672);
        factory.setVirtualHost("/rhb");
        factory.setUsername("rhb");
        factory.setPassword("rhb");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(queue_name, false, false, false, null);

        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println("消费者1接收到消息: "+msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.queueBind(queue_name, exchange_name,"nba.*");
        channel.queueBind(queue_name, exchange_name,"cba.*");
        channel.basicConsume(queue_name, false, consumer);
    }
}
