package com.example.springboot.rabbitMqDemo;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @progrm:TestSpringBoot
 * @Description:默认交换机生产者
 * @Author: leichengxu
 * @Date:2020-08-18 11:56
 */
public class defaultExchangeSend {

  public static final String QUEUE_NAME="queue.demo.first";
  public static final String ROUTING_KEY="key.demo.first";
  private static final String MESSAGE="测试默认交换机DEMO.first";

  public static void main(String[] args) throws IOException, TimeoutException {
    Channel channel = rabbitMqConnectionUtil.getChannel("默认交换机生产者的信道");

    // 声明队列 (队列名, 是否持久化, 是否排他(是否独一队列，同一个Connection(用单例模式实现)，那么这两个消费者也是可以共享这个排他队列的), 是否自动删除, 队列属性);
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);

    //发布消息 (交换机名, Routing key（如果不显示声明交换机,那此处为默认为队列名）,找对应队列 ,队列无消费者, 消息属性, 消息体)
    //channel.basicPublish("",QUEUE_NAME,false,false,null,MESSAGE.getBytes());
    //System.out.println("[defaultExchangeSend]:"+MESSAGE+"已发送！");

    for (int i=0;i<50;i++){
      String message=MESSAGE+(i+1);
      channel.basicPublish("",QUEUE_NAME,false,false,null,message.getBytes());
      System.out.println("[defaultExchangeSend]:"+message+"已发送！");
    }

    //关闭信道
    channel.close();

  }



}
