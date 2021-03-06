package com.example.springboot;

import feign.Logger;
import feign.Logger.Level;
import feign.codec.ErrorDecoder;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@SpringBootApplication
@EnableHystrix
@EnableFeignClients
@EnableSwagger2
@MapperScan("com.example.springboot.mapper")
//@EnableKafka
public class SpringbootApplication {

  @GetMapping("/")
  public static String index(){
    return "hello";
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringbootApplication.class, args);
  }

  @Bean
  Logger.Level feignLoggerLevel() {
    return Level.FULL;
  }



}
