package com.example.springboot.rabbitMqDemo;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @progrm:TestSpringBoot
 * @Description:直连形交换机消费者一号
 * @Author: leichengxu
 * @Date:2020-08-18 14:51
 */
public class directExchangeConsumerOne {
  public static final String EXCHANGE="exchange.demo.there";
  public static final String QUEUE_NAME="queue.demo.there";
  public static final String ROUTING_KEY="key.demo.there";
  private static final String MESSAGE="测试直连形交换机DEMO.there";

  public static void main(String[] args) throws IOException, TimeoutException {

    Channel channel = rabbitMqConnectionUtil.getChannel("直连形交换机消费者一号信道");
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);
    channel.queueBind(QUEUE_NAME,EXCHANGE,ROUTING_KEY,null);
    DefaultConsumer consumer = new DefaultConsumer(channel){
      @Override
      public  void  handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body){
        System.out.println("直连形交换机消费者一号:"+new String(body));
      }
    };
    channel.basicConsume(QUEUE_NAME,true,consumer);
  }

}
