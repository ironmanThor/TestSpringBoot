package com.example.springboot.rabbitMqTest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-17 14:42
 */
public class ConnectionUtil {
  public static Channel getChannelInstance(String connectionDescription) {
    try {
      ConnectionFactory connectionFactory = getConnection();
      Connection connection = connectionFactory.newConnection(connectionDescription);
      return connection.createChannel();
    } catch (Exception e) {
      throw new RuntimeException("获取Channel连接失败");
    }
  }
  public static ConnectionFactory getConnection() throws IOException, TimeoutException {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("127.0.0.1");
    connectionFactory.setPort(5672);
    connectionFactory.setVirtualHost("/test");
    connectionFactory.setUsername("newadmin");
    connectionFactory.setPassword("newpassword");
    // 网络异常自动连接恢复
    connectionFactory.setAutomaticRecoveryEnabled(true);
    // 每10秒尝试重试连接一次
    connectionFactory.setNetworkRecoveryInterval(10000);

    return connectionFactory;
  }
}
