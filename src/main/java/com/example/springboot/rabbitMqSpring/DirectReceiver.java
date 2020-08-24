package com.example.springboot.rabbitMqSpring;

import java.util.Map;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-24 14:48
 */
@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
public class DirectReceiver {

  @RabbitHandler
  public void process(Map testMessage) {
    System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
  }

}
