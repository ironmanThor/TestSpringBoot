package com.example.springboot.Util;

import com.example.springboot.EnumZidingyi.SystemErrorType;
import lombok.Builder;
import lombok.Data;

/**
 * 结果集
 *
 * @author qiaoliang.Mr
 * @date 2019/8/23 15:01
 */
@Builder
@Data
public class ApiErrorResult<T> {

  public static final Integer STATE_ERROR = 409;

  private Integer code;

  private String message;

  /**
   * @param errorType
   */
  public ApiErrorResult(SystemErrorType errorType) {
    this.code = errorType.getCode();
    this.message = errorType.getMessage();
  }

  public ApiErrorResult(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  /**
   * 系统异常类并返回结果数据
   *
   * @return Result
   */
  public static <T> ApiErrorResult<T> fail() {
    return new ApiErrorResult<T>(SystemErrorType.SYSTEM_ERROR);
  }

  /**
   * 自定义返回结果数据
   *
   * @return Result
   */
  public static <T> ApiErrorResult<T> fail(String message) {
    return new ApiErrorResult<T>(STATE_ERROR, message);
  }

  /**
   * token解析失败返回结果数据.
   *
   * @return Result
   */
  public static <T> ApiErrorResult<T> failToken() {
    return new ApiErrorResult<T>(SystemErrorType.UNAUTHORIZED_ERROR.getCode(),
        SystemErrorType.UNAUTHORIZED_ERROR.getMessage());
  }

  /**
   * 自定义返回结果数据
   *
   * @return Result
   */
  public static <T> ApiErrorResult<T> fail(SystemErrorType errorType) {
    return new ApiErrorResult<T>(errorType);
  }
}
