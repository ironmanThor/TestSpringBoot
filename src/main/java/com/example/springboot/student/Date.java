package com.example.springboot.student;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @progrm:TestSpringBoot
 * @Description:时间问题
 * @Author: leichengxu
 * @Date:2020-07-21 10:17
 */
public class Date {


  public static void  main(String[] args){
    LocalDateTime dateTime= LocalDateTime.now();
    System.out.println(dateTime);
    System.out.println(dateTime.getYear());
    System.out.println(dateTime.getDayOfYear());
    System.out.println(dateTime.getDayOfMonth());
    System.out.println(dateTime.getDayOfWeek());
    System.out.println("_________________________");
    System.out.println(dateTime.format(DateTimeFormatter.ISO_DATE));
    System.out.println(dateTime.format(DateTimeFormatter.BASIC_ISO_DATE));
    System.out.println(dateTime.format(DateTimeFormatter.ofPattern("yyyy:MM:dd")));
  }

}
