package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@SpringBootApplication
@EnableHystrix
@EnableFeignClients
@EnableSwagger2
//@EnableKafka
public class SpringbootApplication {

  @GetMapping("/")
  public static String index(){
    return "hello";
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringbootApplication.class, args);
  }

}
