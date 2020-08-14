package com.example.springboot.EnumZidingyi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 业务异常枚举类.
 *
 * @author zhangliangrui
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SystemErrorType {
  /**
   * 业务异常提示
   **/
  UNAUTHORIZED_ERROR(401, "无权访问!"),
  SYSTEM_ERROR(409, "主站应用服务系统异常"),
  INSERT_COMMENT_ERROR(409, "新增评论失败."),
  COMMENT_ERROR(409, "评论有点勤哈，过会儿再评论呗"),
  INSERT_REPLY_ERROR(409, "新增回复失败."),
  USER_ERROR(409, "用户不存在!"),
  OPEN_COURSE_ERROR(409,"该用户只有学员角色，后台不予开通"),

  COURSE_STATUS_ERROR(409,"输入的课程发布状态类型有问题！"),
  DISPLAY_CHAPTER_STATUS_ERROR(409,"输入的课时发布状态类型有问题！"),
  COURSE_NATURE_TYPE_ERROR(409,"输入的课程性质类型有问题！"),
  COURSE_SPECIAL_TYPE_ERROR(409,"输入的课程特殊类型有问题！"),
  STAGE_TYPE_ERROR(409,"输入的期类型不正确！"),
  LIVE_STATUS_ERROR(409,"输入的直播状态类型有问题！"),
  COLLEGE_STATUS_ERROR(409,"学院的状态类型有问题！")
  ;
  /**
   * 错误类型码
   */
  private Integer code;
  /**
   * 错误类型描述信息
   */
  private String message;
}
