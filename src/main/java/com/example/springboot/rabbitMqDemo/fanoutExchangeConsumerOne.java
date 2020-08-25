package com.example.springboot.rabbitMqDemo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @progrm:TestSpringBoot
 * @Description:扇形交换机消费者一号
 * @Author: leichengxu
 * @Date:2020-08-18 14:18
 */
public class fanoutExchangeConsumerOne {
  public static final String EXCHANGE="exchange.demo.two";
  public static final String QUEUE_NAME="queue.demo.two";
  public static final String ROUTING_KEY="key.demo.two";
  private static final String MESSAGE="测试扇形交换机DEMO.two";
  public static final String DLX_EXCHANGE_NAME = "dlx";

  public static void main(String[] args) throws IOException, TimeoutException {
    Channel channel = rabbitMqConnectionUtil.getChannel("扇形交换机消费者一号的信道");


    // 声明交换机 (交换机名, 交换机类型, 是否持久化, 是否自动删除, 是否是内部交换机, 交换机属性);声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
    channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.FANOUT,false,false,false,null);

    // 声明队列 (队列名, 是否持久化, 是否排他(是否独一队列，同一个Connection(用单例模式实现)，那么这两个消费者也是可以共享这个排他队列的), 是否自动删除, 队列属性);
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);

    // 将队列Binding到交换机上 (队列名, 交换机名, Binding key(Routing key), 绑定属性);
    channel.queueBind(QUEUE_NAME,EXCHANGE,ROUTING_KEY,null);

    DefaultConsumer consumer = new DefaultConsumer(channel){
      @Override
      public  void  handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        System.out.println("扇形交换机消费者一号："+new String(body));
        //拒绝接受消息并不重新加入到队列 一次拒绝一条
        channel.basicReject(envelope.getDeliveryTag(),false);
      }
    };
    boolean autoAck=false;
    channel.basicConsume(QUEUE_NAME,autoAck,consumer);
  }

}
