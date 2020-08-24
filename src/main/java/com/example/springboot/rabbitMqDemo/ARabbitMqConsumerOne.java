package com.example.springboot.rabbitMqDemo;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-20 14:24
 */
public class ARabbitMqConsumerOne {

  private static final String EXCHANGE="AexchangeTest";
  private static final String QUEUE_NAME="AqueueTest";
  private static final String ROUTING_KEY="AroutingkeyTest";

  public static void main(String[] args) throws IOException, TimeoutException {
    Channel channel = rabbitMqConnectionUtil.getChannel("cead");
    channel.basicQos(1);

    DefaultConsumer consumer =new DefaultConsumer(channel){
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
        String s = new String(body);
        if (s.startsWith("测试")){
          System.out.println(s);
          channel.basicAck(envelope.getDeliveryTag(),false);
        }else {
          channel.basicNack(envelope.getDeliveryTag(),false,false);
        }
      }
    };
    channel.basicConsume(QUEUE_NAME,false,consumer);

  }

}
