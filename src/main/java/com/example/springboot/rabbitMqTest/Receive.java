package com.example.springboot.rabbitMqTest;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import lombok.SneakyThrows;

/**
 * @progrm:TestSpringBoot
 * @Description:消费者
 * @Author: leichengxu
 * @Date:2020-08-17 14:50
 */
public class Receive {

  private final static String QUEUE_NAME="test_simple_queue";


  public static void main(String[] args) throws IOException, TimeoutException {
    Channel channel = ConnectionUtil.getChannelInstance("消费者二号");

    //声明队列 (队列名, 是否持久化, 是否排他, 是否自动删除, 队列属性);
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);

    // 声明交换机 (交换机名, 交换机类型, 是否持久化, 是否自动删除, 是否是内部交换机, 交换机属性);
    channel.exchangeDeclare("rabbitmq.test", BuiltinExchangeType.TOPIC, true, false, false, new HashMap<>());

    // 将队列Binding到交换机上 (队列名, 交换机名, Routing key, 绑定属性);
    channel.queueBind(QUEUE_NAME,"rabbitmq.test", "lcx", new HashMap<>());

    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
      @SneakyThrows
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        /*System.out.println(consumerTag);
        System.out.println(envelope.toString());
        System.out.println(properties.toString());*/
        System.out.println("消息内容:" + new String(body));
        Thread.sleep(1);
        //手动回执消息
        channel.basicAck(envelope.getDeliveryTag(),false);
      }
    };
    //自动应答关闭 防止消费者挂了生产者也没了消息 true的时候生产者发完就自动删除消息
    boolean autoAck =false;
    //每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
    channel.basicQos(1);
    channel.basicConsume(QUEUE_NAME,autoAck,defaultConsumer);
  }

}
