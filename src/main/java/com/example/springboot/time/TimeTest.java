package com.example.springboot.time;

import cn.hutool.core.date.BetweenFormater.Level;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-07-29 13:59
 */
public class TimeTest {
  public static Calendar c;
  public static int time = 60*60*60;
  public static  long startTime;
  public static long endTime;
  public static Date data;
  public static long midTime;
  public static String ce="1596018206000";



  public static void main(String[] args){

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//设置为东八区
    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    Date newDate = new Date();
    String dateStr = sdf.format(newDate);
    Date date1 = DateUtil.parse(dateStr);
    System.out.println(dateStr);

    String dateStr2 = "2020-08-14 18:00:00";
    Date date2 = DateUtil.parse(dateStr2);
    long betweenDay = DateUtil.between(date1, date2, DateUnit.MINUTE);
    while (betweenDay!=0){
      betweenDay--;
      newDate = new Date();
      dateStr = sdf.format(newDate);
      date1 = DateUtil.parse(dateStr);
      String formatBetween = DateUtil.formatBetween(date1, date2, Level.SECOND);
      System.out.println(formatBetween);
      try {
        Thread.sleep(1000*10);

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }


  }

  private static void time3(){
    while (midTime>0){
      midTime--;
      long hh =midTime /60/60%60;
      long mm =midTime/60 %60;
      long ss = midTime %60;
      System.out.println(hh+mm+ss);
      try {
        Thread.sleep(1000);
      }catch (InterruptedException e){
        e.printStackTrace();
      }
    }
  }

  private static void time2() {

    while (midTime > 0) {
      midTime--;
      long hh = midTime / 60 / 60 % 60;
      long mm = midTime / 60 % 60;
      long ss = midTime % 60;
      System.out.println("还剩" + hh + "小时" + mm + "分钟" + ss + "秒");
      try {
        Thread.sleep(1000);

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private static  void time1(){
    Timer timer=new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        midTime--;
        long hh =midTime /60/60%60;
        long mm =midTime /60 % 60;
        long ss =midTime %60;
        System.out.println("剩余"+hh+"小时"+mm+"分钟"+ss+"秒");
      }
    },0,1000);
  }
}
