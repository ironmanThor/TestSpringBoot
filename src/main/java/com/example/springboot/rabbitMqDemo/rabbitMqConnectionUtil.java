package com.example.springboot.rabbitMqDemo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @progrm:TestSpringBoot
 * @Description:创建连接以及信道
 * @Author: leichengxu
 * @Date:2020-08-18 11:41
 */
public class rabbitMqConnectionUtil {

  public static Channel getChannel(String channelTag) throws IOException, TimeoutException {
    ConnectionFactory factory = rabbitMqConnectionUtil.getConnection();
    Connection connection1 = factory.newConnection(channelTag);
    return connection1.createChannel();
  }

  private static ConnectionFactory getConnection() {
    //创建TCP连接工具
    ConnectionFactory factory = new ConnectionFactory();
    //设置端口号
    factory.setPort(5672);
    //设置IP
    factory.setHost("127.0.0.1");
    //设置虚拟机
    factory.setVirtualHost("testhost");
    //账号
    factory.setUsername("newadmin");
    //密码
    factory.setPassword("newpassword");

    return factory;
  }

}
