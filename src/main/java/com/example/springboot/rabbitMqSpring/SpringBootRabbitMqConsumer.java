package com.example.springboot.rabbitMqSpring;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-24 14:41
 */
@Configuration
public class SpringBootRabbitMqConsumer {

  @Bean
  public Queue TestDirectQueue(){
    return new Queue("TestDirectQueue",true);
  }

  //Direct交换机 起名：TestDirectExchange
  @Bean
  DirectExchange TestDirectExchange() {
    return new DirectExchange("TestDirectExchange");
  }

  //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
  @Bean
  Binding bindingDirect() {
    return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
  }
}
