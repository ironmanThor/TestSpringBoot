package com.example.springboot.rabbitMqDemo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import lombok.SneakyThrows;

/**
 * @progrm:TestSpringBoot
 * @Description:默认交换机消费者二号
 * @Author: leichengxu
 * @Date:2020-08-18 13:40
 */
public class defaultExchangeConsumerTwo {
  public static final String QUEUE_NAME="queue.demo.first";
  public static final String ROUTING_KEY="key.demo.first";
  private static final String MESSAGE="测试默认交换机DEMO.first";

  public static void main(String[] args) throws IOException, TimeoutException {
    Channel channel = rabbitMqConnectionUtil.getChannel("默认交换机消费者二号信道");
    channel.queueDeclare(QUEUE_NAME,false,false,true,null);
    //channel.basicQos(1);
    DefaultConsumer consumer = new DefaultConsumer(channel){
      @SneakyThrows
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body){
        System.out.println("默认交换机消费者二号："+new String(body));
        Thread.sleep(2);
        channel.basicAck(envelope.getDeliveryTag(),false);
      }
    };
    boolean autoAck =false;
    String s = channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    //channel.basicCancel(s); s为服务端生成的消费者标识 关闭消费者订阅
  }


}
