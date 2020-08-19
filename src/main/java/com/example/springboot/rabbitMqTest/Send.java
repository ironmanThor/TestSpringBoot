package com.example.springboot.rabbitMqTest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-17 14:36
 */
public class Send {

  private  final  static String QUEUE_NAME="test_simple_queue";

  public static void main(String[] args) throws IOException, TimeoutException {
    Connection connection= (Connection) ConnectionUtil.getConnection();
    Channel channel = connection.createChannel();
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);
    String message="测试消息中间件";
    channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
    System.out.println("Send发送");
    channel.close();
    connection.clearBlockedListeners();
  }

}


/*public class Send {
  private static  final  String QUEUE_NAME = "test_queue_confirm2";
  public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
    Connection connection = ConnectionUtils.getConnection();
    Channel channel = connection.createChannel();
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);
    //开启confirm模式
    channel.confirmSelect();

    //未确认的消息放入
    SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

    //添加监听通道
    channel.addConfirmListener(new ConfirmListener() {
      //没有问题的handleAck 成功
      @Override
      public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        if(multiple){   //多个的
          System.out.println("handleAck:multiple");
          confirmSet.headSet(deliveryTag+1).clear();
        }else{          //单个的
          System.out.println("handleAck:multiple false");
          confirmSet.remove(deliveryTag);
        }
      }
      //有问题的反馈Nack 失败
      @Override
      public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        if(multiple){
          System.out.println("handleNack:multiple");
          confirmSet.headSet(deliveryTag+1).clear();
        }else{
          System.out.println("handleNack:multiple false");
          confirmSet.remove(deliveryTag);
        }
      }
    });

    String msg = "hello Confirm11111111";
    while(true){
      long seqNo = channel.getNextPublishSeqNo();
      channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
      confirmSet.add(seqNo);
    }
  }
}*/
/*
// 声明交换机 (交换机名, 交换机类型, 是否持久化, 是否自动删除, 是否是内部交换机, 交换机属性);
        channel.exchangeDeclare("rabbitmq.wj", BuiltinExchangeType.DIRECT, true, false, false, new HashMap<>());

    // 设置消息属性 发布消息 (交换机名, Routing key, 可靠消息相关属性 后续会介绍, 消息属性, 消息体);
    AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().deliveryMode(2).contentType("UTF-8").build();
    channel.basicPublish("rabbitmq.wj", "add", false, basicProperties, "body中的消息内容！".getBytes());*/
