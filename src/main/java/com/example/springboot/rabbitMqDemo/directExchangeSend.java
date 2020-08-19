package com.example.springboot.rabbitMqDemo;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @progrm:TestSpringBoot
 * @Description:直连形交换机生产者
 * @Author: leichengxu
 * @Date:2020-08-18 14:47
 */
public class directExchangeSend {
  public static final String EXCHANGE="exchange.demo.there";
  public static final String QUEUE_NAME="queue.demo.there";
  public static final String ROUTING_KEY="key.demo.there";
  private static final String MESSAGE="测试直连形交换机DEMO.there";

  public static void main(String[] args) throws IOException, TimeoutException {
    Channel channel = rabbitMqConnectionUtil.getChannel("直连形交换机生产者信道");
    channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT,false,false,false,null);
    channel.basicPublish(EXCHANGE,ROUTING_KEY,false,false,null,MESSAGE.getBytes());
    System.out.println("[directExchangeSend]:"+MESSAGE+"已发送！");
    channel.close();
  }
}
