package com.example.springboot.rabbitMqDemo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ReturnListener;
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

    Channel channel = rabbitMqConnectionUtil.getChannel("死信以及队列属性以及消息属性");


    //要进行死信队列的声明:
    channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
    channel.queueDeclare("dlx.queue", true, false, false, null);
    channel.queueBind("dlx.queue", "dlx.exchange", "dlx.dead");
    channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC,false,false,null);
    Map<String,Object> map = new HashMap<>();
    map.put("x-max-priority",10);
    map.put("x-expires", 20 * 1000);
    map.put("x-dead-letter-exchange", "dlx.exchange");
    map.put("x-dead-letter-routing-key", "dlx.dead");
    channel.queueDeclare(QUEUE_NAME,false,false,true,map);
    channel.queueBind(QUEUE_NAME,EXCHANGE,ROUTING_KEY,null);

    for (int i =1;i<11;i++){
      String message = null;
      if (i<6){
        message = "测试优先级"+i;
      }else {
        message = "首先测试优先级"+i;
      }
      AMQP.BasicProperties.Builder builder = new Builder();
      Map<String,Object> headers = new HashMap<String, Object>();
      headers.put("name", "七夜雪");
      headers.put("name1", "七夜雪1");
      builder.priority(i).deliveryMode(2).contentEncoding("UTF-8");
      //builder.headers(headers);
      BasicProperties build = builder.build();
      channel.basicPublish(EXCHANGE,ROUTING_KEY,false,false,build,message.getBytes());

      System.out.println("已发送消息："+message+"["+i+"]");
    }
  }

}
