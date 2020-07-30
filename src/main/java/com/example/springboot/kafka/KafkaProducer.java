/*
package com.example.springboot.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


*/
/**
 * @progrm:TestSpringBoot
 * @Description:消息生成者
 * @Author: leichengxu
 * @Date:2020-06-22 19:28
 *//*

@RestController
@RequestMapping("/kafka")
public class KafkaProducer {
  private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

  public static final String TOPIC_ONE="topic.one";

  @Autowired
  private KafkaTemplate<String,String> kafkaTemplate;

  @RequestMapping("/send")
  public String send(@RequestParam("msg") String msg) {
    logger.info("要发送的消息：{}", msg);
    ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC_ONE, msg);
    future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
      @Override
      public void onFailure(Throwable throwable) {
        logger.info(TOPIC_ONE + "- 生产者 发送消息失败 ：" + throwable.getMessage());
      }

      @Override
      public void onSuccess(SendResult<String, String> stringStringSendResult) {
        logger.info(TOPIC_ONE + " - 生产者 发送消息成功 ：" + stringStringSendResult.toString());
      }
    });
    return "0000";
  }

}






*/
