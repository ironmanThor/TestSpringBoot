package com.example.springboot.aop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 日志实体
 *
 * @author leichegnxu
 * @since 2020-04-27
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BehaviorLog {

  /**
   * 终端
   */
  private String extremity;
  /**
   * 模块名称
   */
  private String operationUnit;
  /**
   * 操作类型
   */
  private String operationType;
  /**
   * 方法名
   */
  private String method;
  /**
   * 请求参数
   */
  private String args;
  /**
   * 操作人id
   */
  private String operationUserId;
  /**
   * 日志描述
   */
  private String describe;
  /**
   * 方法运行时间
   */
  private Long runTime;
  /**
   * 方法返回值
   */
  private String returnValue;
  /**
   * 创建日期
   */
  private String createTime;
}
