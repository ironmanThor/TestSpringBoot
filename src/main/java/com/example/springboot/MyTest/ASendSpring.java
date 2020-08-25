package com.example.springboot.MyTest;

import java.util.Date;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-17 16:36
 */
public class ASendSpring {

  // spring boot 为我们提供的包装类
  @Autowired
  private AmqpTemplate rabbitTemplate;

  public void send() {
    String context = "hello " + new Date();
    System.out.println("Sender : " + context);
// 调用 发送消息的方法
    this.rabbitTemplate.convertAndSend("hello", context);
  }


}
