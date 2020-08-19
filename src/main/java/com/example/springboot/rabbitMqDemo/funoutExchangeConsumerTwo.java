package com.example.springboot.rabbitMqDemo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javax.xml.crypto.Data;

/**
 * @progrm:TestSpringBoot
 * @Description:扇形交换机消费者二号
 * @Author: leichengxu
 * @Date:2020-08-18 14:31
 */
public class funoutExchangeConsumerTwo {
  public static final String EXCHANGE="exchange.demo.two";
  public static final String QUEUE_NAME="queue.demo.two.two";
  public static final String ROUTING_KEY="key.demo.two";
  private static final String MESSAGE="测试扇形交换机DEMO.two";

  public static void main(String[] args) throws IOException, TimeoutException {
    Channel channel = rabbitMqConnectionUtil.getChannel("扇形交换机消费者二号的信道");
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);
    channel.queueBind(QUEUE_NAME,EXCHANGE,ROUTING_KEY,null);
    DefaultConsumer consumer =new DefaultConsumer(channel){
      @Override
      public void  handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,byte[] body){
        System.out.println("扇形交换机消费者二号："+new String(body));
      }
    };
    channel.basicConsume(QUEUE_NAME,true,consumer);
  }

}
