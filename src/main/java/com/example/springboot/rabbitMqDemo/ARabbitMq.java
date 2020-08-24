package com.example.springboot.rabbitMqDemo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @progrm:TestSpringBoot
 * @Description:生产者
 * @Author: leichengxu
 * @Date:2020-08-20 13:34
 */
public class ARabbitMq {

  private static final String EXCHANGE="AexchangeTest";
  private static final String QUEUE_NAME="AqueueTest";
  private static final String ROUTING_KEY="AroutingkeyTest";

  public static void main(String[] args) throws IOException, TimeoutException {

    Channel channel = rabbitMqConnectionUtil.getChannel("自我测试");


    //要进行死信队列的声明:
    channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
    channel.queueDeclare("dlx.queue", true, false, false, null);
    channel.queueBind("dlx.queue", "dlx.exchange", "#");
    channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC,false,true,null);
    Map<String,Object> map = new HashMap<>();
    map.put("x-max-priority",10);
    map.put("x-dead-letter-exchange", "dlx.exchange");
    channel.queueDeclare(QUEUE_NAME,false,false,true,map);
    channel.queueBind(QUEUE_NAME,EXCHANGE,ROUTING_KEY,null);

    for (int i =0;i<10;i++){
      String message = null;
      if (i<5){
        message = "测试优先级"+i;
      }else {
        message = "首先测试优先级"+i;
      }
      AMQP.BasicProperties.Builder builder = new Builder();
      builder.priority(i);

      BasicProperties build = builder.build();
      channel.basicPublish(EXCHANGE,ROUTING_KEY,false,false,build,message.getBytes());
      System.out.println("已发送消息："+message+"["+i+"]");
    }
  }

}
