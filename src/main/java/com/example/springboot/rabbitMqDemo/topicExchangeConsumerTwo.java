package com.example.springboot.rabbitMqDemo;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @progrm:TestSpringBoot
 * @Description:主题交换机消费者二号
 * @Author: leichengxu
 * @Date:2020-08-18 15:22
 */
public class topicExchangeConsumerTwo {
  public static final String EXCHANGE = "exchange.demo.four";
  public static final String QUEUE_NAME = "queue.demo.four.e";
  public static final String ROUTING_KEY = "key.demo.four";
  private static final String MESSAGE = "测试主题交换机DEMO.four";

  public static void main(String[] args) throws IOException, TimeoutException {
    Channel channel = rabbitMqConnectionUtil.getChannel("主题交换机消费者二号信道");
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    channel.queueBind(QUEUE_NAME, EXCHANGE, "key.*.*", null);
    DefaultConsumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) {
        System.out.println("主题交换机消费者二号:"+new String(body));
      }
    };
    channel.basicConsume(QUEUE_NAME,true,consumer);
  }
}
