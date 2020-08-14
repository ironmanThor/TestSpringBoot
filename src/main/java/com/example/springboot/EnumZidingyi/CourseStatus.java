package com.example.springboot.EnumZidingyi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 课程状态的枚举类型。
 *
 * @author leichengxu
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CourseStatus {
  /**
   * 未发布。
   */
  DRAFT("DRAFT", "未发布"),

  /**
   * 已发布。
   */
  PUBLISHED("PUBLISHED", "已发布"),

  /**
   * 已关闭。
   */
  CLOSED("CLOSED", "已关闭");

  /**
   * 课程状态的值。
   */
  private String value;

  /**
   * 课程状态的中文描述。
   */
  private String text;

}
