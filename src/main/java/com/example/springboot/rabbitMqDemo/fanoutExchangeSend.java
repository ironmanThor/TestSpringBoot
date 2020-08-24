package com.example.springboot.rabbitMqDemo;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @progrm:TestSpringBoot
 * @Description:扇形交换机生产者
 * @Author: leichengxu
 * @Date:2020-08-18 14:06
 */
public class fanoutExchangeSend {
  public static final String EXCHANGE="exchange.demo.two";
  public static final String QUEUE_NAME="queue.demo.two";
  public static final String ROUTING_KEY="key.demo.two";
  private static final String MESSAGE="测试扇形交换机DEMO.two";

  public static void main(String[] args) throws IOException, TimeoutException {
    Channel channel = rabbitMqConnectionUtil.getChannel("扇形交换机生产者信道");

    // 声明交换机 (交换机名, 交换机类型, 是否持久化, 是否自动删除, 是否是内部交换机, 交换机属性);
    channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.FANOUT,false,false,false,null);

    //发布消息 (交换机名, Routing key,找对应队列 ,队列无消费者, 消息属性（MessageProperties.PERSISTENT_TEXT_PLAIN 消息持久化）, 消息体)
    channel.basicPublish(EXCHANGE,ROUTING_KEY,false,false,null ,MESSAGE.getBytes());
    System.out.println("[fanoutExchangeSend]:"+MESSAGE+"已发送！");

    channel.close();
  }

}
