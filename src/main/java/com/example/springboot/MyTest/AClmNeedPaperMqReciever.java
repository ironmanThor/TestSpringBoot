package com.example.springboot.MyTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-17 16:37
 */
@Component
@Slf4j
@EnableScheduling
public class AClmNeedPaperMqReciever {
  //1. @RabbitListener(queues = "myQueue") // 不能自动创建队列
  //2. 自动创建队列 @RabbitListener(queuesToDeclare = @Queue("myQueue"))
  //3. 自动创建, Exchange和Queue绑定
  @RabbitListener(bindings = @QueueBinding(
      value = @Queue("myQueue"),
      exchange = @Exchange("myExchange")
  ))
  public void process(String message) {
    log.info("MqReceiver: {}", message);
  }

  /**
   * 数码供应商服务 接收消息
   * @param message
   */
  @RabbitListener(bindings = @QueueBinding(
      exchange = @Exchange("myOrder"),
      key = "computer",
      value = @Queue("computerOrder")
  ))
  public void processComputer(String message) {

    log.info("computer MqReceiver: {}", message);
  }


  /**
   * 水果供应商服务 接收消息
   * @param message
   */
  @RabbitListener(bindings = @QueueBinding(
      exchange = @Exchange("myOrder"),
      key = "fruit",
      value = @Queue("fruitOrder")
  ))
  public void processFruit(String message) {
    log.info("fruit MqReceiver: {}", message);
  }
}