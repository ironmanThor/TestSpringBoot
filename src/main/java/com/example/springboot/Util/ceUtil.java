package com.example.springboot.Util;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @progrm:pc-admin-service
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-03 16:08
 */
@Component
public class ceUtil {



//      手机号掩码处理 1
//      1.小于或等于5位的,完全显示
//      2.大于5位的:前3位显示,后2位显示,其余埋"*".
  public static String mobile(String mobile) {
    if(StringUtils.isBlank(mobile) || mobile.length() <= 5) {
      return mobile;
    }
    return wordMask(mobile, 3, 2, "*");
  }

//  qq掩码处理 1
//      1.小于或等于4位的,完全显示.
//      2.大于4位的:只显示前四位,其余埋"*".
  public static String qq(String mobile) {
    if(StringUtils.isBlank(mobile) || mobile.length() <= 4) {
      return mobile;
    }
    return wordMask(mobile, 4, 0, "*");
  }
//  微信掩码处理
//      1.小于或等于4位的,完全显示.
//      2.前4位等于='wxid'的,从第9位开始埋'*'.
//      3.大于5位的,前4位不等于='wxid'的,显示前4位,之后埋'*'.
  public static String weixin(String mobile) {
    if(StringUtils.isBlank(mobile) || mobile.length() <= 4) {
      return mobile;
    }
    if(StringUtils.isNotBlank(mobile) && mobile.length() > 4 && StringUtils.substring(mobile,4).equals("wxid")) {
      return wordMask(mobile, 9, 0, "*");
    }
    return wordMask(mobile, 4, 0, "*");
  }


  /**
   * @param word 被脱敏的字符
   * @param startLength 被保留的开始长度 前余n位
   * @param endLength 被保留的结束长度 后余n位
   * @param pad 填充字符
   * */
  public static String wordMask(String word,int startLength ,int endLength,String pad)    {

    if (startLength + endLength > word.length()) {
      return StringUtils.leftPad("", word.length() - 1, pad);
    }

    String startStr = word.substring(0, startLength);

    String endStr = word.substring(word.length() - endLength, word.length());

    return startStr + StringUtils.leftPad("", word.length() - startLength - endLength, pad) + endStr;

  }


}
