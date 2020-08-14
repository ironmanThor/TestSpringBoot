package com.example.springboot.Util;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获拦截.
 *
 * @author zhangliangrui
 */
@ControllerAdvice
public class GlobalExceptionHandler {



  /**
   * 处理409异常，无效的请求数据格式.
   */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  ResponseEntity handleConflictException(IllegalArgumentException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiErrorResult.fail(e.getMessage()));
  }

  /**
   * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
   *
   * @author qiaoliang.Mr
   * @date 2019/8/23 16:49
   */
  @ExceptionHandler(BindException.class)
  public ResponseEntity<?> bindExceptionHandler(BindException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(
        ApiErrorResult.fail(e.getAllErrors().get(0).getDefaultMessage()));
  }

  /**
   * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
   *
   * @return org.springframework.http.ResponseEntity<?>
   * @author qiaoliang.Mr
   * @date 2019/8/23 16:49
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> constraintViolationExceptionHandler(ConstraintViolationException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(
        ApiErrorResult.fail(e.getConstraintViolations().iterator().next().getMessage()));
  }

  /**
   * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
   *
   * @return org.springframework.http.ResponseEntity<?>
   * @author qiaoliang.Mr
   * @date 2019/8/23 16:49
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ResponseEntity<?> methodArgumentNotValidExceptionHandler(
      MethodArgumentNotValidException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(
        ApiErrorResult.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
  }

/*  *//***
   * 自定义异常捕捉
   * @author qiaoliang.Mr
   * @return org.springframework.http.ResponseEntity<?>
   * @date 2019/8/23 16:49
   *//*
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<?> baseException(BusinessException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiErrorResult.fail(e.getErrorType()));
  }

  *//**
   * 400<=和<500之间属于业务异常，进行捕获.
   *//*
  @ExceptionHandler(HystrixBadRequestException.class)
  @ResponseBody
  ResponseEntity handleNotFoundException(HystrixBadRequestException e) {
    val hystrixBadResponse = JSONUtil.toBean(e.getMessage(), HystrixBadResponse.class);
    ApiErrorResult apiErrorResult;
    try{
      apiErrorResult= JSONUtil
          .toBean(hystrixBadResponse.getContent(), ApiErrorResult.class);
    }catch (Exception ex){
      apiErrorResult=new ApiErrorResult(hystrixBadResponse.getStatus(),hystrixBadResponse.getContent());
    }
    if (Objects.nonNull(apiErrorResult.getCode())) {
      return ResponseEntity.status(apiErrorResult.getCode()).body(apiErrorResult);
    }
    return ResponseEntity.status(hystrixBadResponse.getStatus())
        .body(hystrixBadResponse.getContent());
  }*/

}
