package com.example.springboot.datafill.aop;

import com.example.springboot.datafill.executor.DataFillExecutor;
import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-07-10 14:32
 */
@Aspect
@Component
public class DataFillAop {
  @Autowired
  private DataFillExecutor dataFillFactory;

  @Pointcut("execution(* com.example.springboot..*.*(..))")
  public void controllerPointCut(){}

  @Around("controllerPointCut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
    try {
      Object result = joinPoint.proceed();
      MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
      Arrays.stream(methodSignature.getMethod().getAnnotations())
          .filter(annotation -> annotation.annotationType().isAnnotationPresent(RequestMapping.class))
          .findFirst().ifPresent(requestMappingClass ->{
        if(result instanceof ResponseEntity){
          Object body = ((ResponseEntity)result).getBody();
          DataFillExecutor.execute(body);
        }
      });
      return result;
    }catch (Throwable e){
      e.printStackTrace();
      throw e;
    }finally {

    }
  }
}
