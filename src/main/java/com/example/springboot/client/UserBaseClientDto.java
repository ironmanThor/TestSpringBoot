package com.example.springboot.client;


import java.io.Serializable;
import lombok.Data;

/**
 * @Author：zhangliangrui
 * @Description：统一的用户model
 * @Date：19-9-25
 **/
@Data
public class UserBaseClientDto implements Serializable {

  /**
   * 用户ID
   */
  private String userId;
  /**
   * 昵称
   */
  private String nickname;
  /**
   * 头像
   */
  private String avatar;
  /**
   * 个性签名
   */
  private String introduction;
  /**
   * 大鹏号
   */
  private String dpAccount;


}
