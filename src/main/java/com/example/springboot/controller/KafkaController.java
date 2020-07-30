package com.example.springboot.controller;

import com.example.springboot.kafka.KafkaProducerLGQ;
import com.example.springboot.kafka.Order;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-07-22 15:19
 */
@RestController
@RequestMapping(value = "/kakfa")
public class KafkaController {

  @Autowired
  private KafkaProducerLGQ kafkaProducerLGQ;

  @GetMapping("/send")
  public String sendMessage(){
    Order order=new Order();
    order.setId(UUID.randomUUID().toString().replace("-",""))
    .setCreateTime(LocalDateTime.now()).setName("大额订单");
    kafkaProducerLGQ.sendMessage("ces",order.toString());
    return "true";
  }

}
