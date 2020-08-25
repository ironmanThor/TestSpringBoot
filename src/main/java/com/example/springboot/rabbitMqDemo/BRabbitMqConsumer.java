package com.example.springboot.rabbitMqDemo;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-25 14:30
 */
public class BRabbitMqConsumer {

  private static final String EXCHANGE="BexchangeTest";
  private static final String QUEUE_NAME="BqueueTest";
  private static final String ROUTING_KEY="BroutingkeyTest";

  public static void main(String[] args) throws IOException, TimeoutException {
    Channel channel = rabbitMqConnectionUtil.getChannel("return机制消费者");
    channel.basicQos(1);
    channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC,false,true,null);
    Map<String,Object> map = new HashMap<>();
    map.put("x-max-priority",10);
    map.put("x-expires", 20 * 1000);
    channel.queueDeclare(QUEUE_NAME,false,false,true,map);
    channel.queueBind(QUEUE_NAME,EXCHANGE,ROUTING_KEY,null);

    DefaultConsumer consumer =new DefaultConsumer(channel){
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
        String s = new String(body);
        channel.basicAck(envelope.getDeliveryTag(),false);
      }
    };
    channel.basicConsume(QUEUE_NAME,false,consumer);

  }

}
