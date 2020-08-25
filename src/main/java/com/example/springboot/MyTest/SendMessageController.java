package com.example.springboot.MyTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-24 14:46
 */
@RestController
public class SendMessageController {

  private RabbitTemplate rabbitTemplate;

  @GetMapping("/TestRabbitMq")
  public String sendMessage(){
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "test message, hello!";
    String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String,Object> map=new HashMap<>();
    map.put("messageId",messageId);
    map.put("messageData",messageData);
    map.put("createTime",createTime);
    //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
    rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
    return "ok";
  }

}
