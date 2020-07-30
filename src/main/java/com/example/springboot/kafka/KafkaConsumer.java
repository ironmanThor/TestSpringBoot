/*
package com.example.springboot.kafka;

import java.util.Optional;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

*/
/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-06-23 11:40
 *//*

@Component
public class KafkaConsumer {

  private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

  @KafkaListener(topics = KafkaProducer.TOPIC_ONE,groupId = KafkaProducer.TOPIC_ONE)
  public void topic_one(ConsumerRecord<?,?> record,@Header(KafkaHeaders.RECEIVED_TOPIC) String topic){
    Optional<?> message = Optional.ofNullable(record.value());
    if (message.isPresent()){
      Object msg = message.get();
      logger.info("被"+KafkaProducer.TOPIC_ONE+"消费了： +++++++Topic"+topic+",Record : "+ record+",Message :"+msg);
    }
  }


  @KafkaListener(topics = KafkaProducer.TOPIC_ONE,groupId = KafkaProducer.TOPIC_ONE)
  public void topic_two(ConsumerRecord<?,?> record,@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
    Optional<?> message =Optional.ofNullable(record.value());
    if (message.isPresent()){
      Object msg = message.get();
      logger.info("被"+KafkaProducer.TOPIC_ONE+"1111消费了 ： ++++ topic"+topic+",Record :"+record+".Message:"+msg);
    }
  }
}



*/
