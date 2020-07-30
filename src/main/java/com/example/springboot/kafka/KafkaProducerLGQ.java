package com.example.springboot.kafka;

import javax.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-07-22 15:02
 */
@Component
public class KafkaProducerLGQ {

  private  static  final  String TOPIC="star-test";

  @Resource
  private KafkaTemplate<String,String> kafkaTemplate;

  /**
   * 发送消息
   * @param key 消息的键
   * @param messageBody 消息体
   */
  public void sendMessage(String key,String messageBody){
    kafkaTemplate.send(TOPIC,key,messageBody);
  }

}
