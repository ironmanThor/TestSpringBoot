package com.example.springboot.kafka;

import cn.hutool.db.sql.Order;
import com.alibaba.fastjson.JSON;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.aspectj.weaver.ast.Or;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-07-22 15:05
 */
@Component
public class KafkaConsumerLGQ {

  @KafkaListener(topics = "star-test",groupId = "springboot_kafka_demo")
  public void consumer(ConsumerRecord consumerRecord){
    String topic = consumerRecord.topic();
    int partition = consumerRecord.partition();
    String timestampType = consumerRecord.timestampType().toString();
    Date date = new Date(consumerRecord.timestamp());
    Object key = consumerRecord.key();
    Order order = JSON.parseObject((String) consumerRecord.value(), Order.class);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    System.out.println("我的kafka消息消费:"+consumerRecord.toString());
    System.out.println("主题："+topic);
    System.out.println("分区："+partition);
    System.out.println(timestampType+":"+simpleDateFormat.format(date));
    System.out.println("key："+key);
    System.out.println("消费体为："+order.toString());
  }

}
