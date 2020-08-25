package com.example.springboot.MyTest;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-20 16:35
 */
public class test {
  private static final String EXCHANGE="exchange_testhost";
  private static final String DEAD_EXCHANGE="dlx.exchange";
  private static final String QUEUE_NAME="queue_testhost";
  private static final String DEAD_NAME="dlx.queue";
  private static final String ROUTING_KEY="key_testhost";

  public static void main(String[] args) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("127.0.0.1");
    factory.setPort(5672);
    factory.setVirtualHost("/");
    factory.setUsername("newadmin");
    factory.setPassword("newpassword");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.exchangeDeclare(DEAD_EXCHANGE,BuiltinExchangeType.TOPIC,false,true,null);
    channel.queueDeclare(DEAD_NAME,false,false,true,null);
    channel.queueBind(DEAD_NAME,DEAD_EXCHANGE,"#",null);
    channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT,false,true,null);
    Map<String,Object> map = new HashMap<>();
    map.put("x-message-ttl",30000);
    map.put("x-expires",60000);
    map.put("x-max-length",10);
    map.put("x-max-priority",10);
    map.put("x-dead-letter-exchange", DEAD_EXCHANGE);
    channel.queueDeclare(QUEUE_NAME,false,false,true,map);
    channel.queueBind(QUEUE_NAME,EXCHANGE,ROUTING_KEY,null);
    for (int i=0;i<10;i++){
      String message = "汇合"+i;
      AMQP.BasicProperties.Builder builder=new Builder();
      BasicProperties build = builder.priority(i).build();
      try {
        channel.txSelect();
        channel.basicPublish(QUEUE_NAME,ROUTING_KEY,false,false,build,message.getBytes());
        channel.txCommit();
      }catch (Exception e){
        System.out.println(e.getMessage());
        channel.txRollback();
      }
    }

  }

}
