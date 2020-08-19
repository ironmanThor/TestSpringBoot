package com.example.springboot.rabbitMqDemo;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @progrm:TestSpringBoot
 * @Description:主题交换机生产者
 * @Author: leichengxu
 * @Date:2020-08-18 15:11
 */
public class topicExchangeSend {
  public static final String EXCHANGE="exchange.demo.four";
  public static final String QUEUE_NAME="queue.demo.four";
  public static final String ROUTING_KEY="key.demo.four";
  private static final String MESSAGE="测试主题交换机DEMO.four";

  public static void main(String[] args) throws IOException, TimeoutException {
    Channel channel = rabbitMqConnectionUtil.getChannel("主题交换机生产者的信道");
    channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC,false,false,false,null);
    channel.basicPublish(EXCHANGE,ROUTING_KEY,false,null,MESSAGE.getBytes());
    System.out.println("[topicExchangeSend]:"+MESSAGE+"已发送！");
    channel.close();
  }

}
