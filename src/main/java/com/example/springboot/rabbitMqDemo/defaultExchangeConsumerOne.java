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
 * @Description:默认交换机消费者一号
 * @Author: leichengxu
 * @Date:2020-08-18 12:08
 */
public class defaultExchangeConsumerOne {
  public static final String QUEUE_NAME="queue.demo.first";
  public static final String ROUTING_KEY="key.demo.first";
  private static final String MESSAGE="测试默认交换机DEMO.first";

  public static void main(String[] args) throws IOException, TimeoutException {
    Channel channel = rabbitMqConnectionUtil.getChannel("默认交换机消费者一号信道");

    // 声明队列 (队列名, 是否持久化, 是否排他(是否独一队列，同一个Connection(用单例模式实现)，那么这两个消费者也是可以共享这个排他队列的), 是否自动删除, 队列属性);
    channel.queueDeclare(QUEUE_NAME,false,false,true,null);

    //能者多劳模式，但是必须手动返回确认信息
    //channel.basicQos(1);

    DefaultConsumer consumer = new DefaultConsumer(channel){
      @SneakyThrows
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("默认交换机消费者一号："+new String(body));
        Thread.sleep(5);
        //手动返回确认信息
        channel.basicAck(envelope.getDeliveryTag(),false);
      }
    };
    //关闭自动确认
    boolean autoAck = false;
    //监听队列
    channel.basicConsume(QUEUE_NAME,autoAck,consumer);
  }
}
