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
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-25 14:25
 */
public class BRabbitMq {
  private static final String EXCHANGE="BexchangeTest";
  private static final String QUEUE_NAME="BqueueTest";
  private static final String ROUTING_KEY="BroutingkeyTest";

  public static void main(String[] args) throws IOException, TimeoutException {

    Channel channel = rabbitMqConnectionUtil.getChannel("return机制");

    channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC,false,true,null);
    Map<String,Object> map = new HashMap<>();
    map.put("x-max-priority",10);
    map.put("x-expires", 20 * 1000);
    channel.queueDeclare(QUEUE_NAME,false,false,true,map);
    channel.queueBind(QUEUE_NAME,EXCHANGE,ROUTING_KEY,null);

    for (int i =0;i<10;i++){

      AMQP.BasicProperties.Builder builder = new Builder();

      builder.priority(i).contentEncoding("UTF-8");

      BasicProperties build = builder.build();
      String message = null;
      if (i<5){
        message = "测试消息到达"+i;
        channel.basicPublish(EXCHANGE,ROUTING_KEY,true,false,build,message.getBytes());
      }else {
        message = "首先测试消息到达"+i;
        channel.basicPublish(EXCHANGE,"NOTFOUND",true,false,build,message.getBytes());
      }
      System.out.println("已发送消息："+message+"["+i+"]");
    }
    channel.addReturnListener(new ReturnListener() {
      @Override
      public void handleReturn(int replyCode, String replyText, String exchange, String routingKey,
          AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("没有到达目的地的消息：：" + new String(body));
      }
    });
  }

}
