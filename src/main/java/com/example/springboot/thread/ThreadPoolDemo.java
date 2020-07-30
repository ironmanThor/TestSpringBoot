package com.example.springboot.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-07-09 11:22
 */
public class ThreadPoolDemo {

  public static void main(String[] args){
    ExecutorService service = Executors.newFixedThreadPool(3);
    MyCallable myCallable =new MyCallable();
    service.submit(myCallable);
    service.submit(myCallable);
    service.submit(myCallable);
  }

}
