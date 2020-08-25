package com.example.springboot.MyTest;

import java.util.concurrent.TimeUnit;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-17 17:13
 */
public class Test01 {
  public volatile int flag = 0;

  public static  void main(String[] args) throws InterruptedException {
    Test01  test01 = new Test01();
    new Thread(() -> {
      while (test01.flag == 0){
      }
      System.out.println("flag == 1结束");
    }).start();
    TimeUnit.SECONDS.sleep(1);
    System.out.println("我要结束循环");
    test01.flag = 1;
  }
}
