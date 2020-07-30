package com.example.springboot.thread;

import java.util.concurrent.Callable;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-07-09 11:18
 */
public class MyCallable implements Callable {

  @Override
  public Object call() throws Exception {

    System.out.println("MYCALLABLE:我想要个教练！");
    Thread.sleep(2000);
    System.out.println("MYCALLABLE:教练来了："+Thread.currentThread().getName());
    System.out.println("教练教完我回到了家");
    return null;
  }
}
