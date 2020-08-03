package com.example.springboot.aop;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * 拦截dapeng.com专用切面
 *
 * @author sqf
 */
@Slf4j
@Aspect
@Configuration
public class DoMainAspect {

  /**
   * 拦截controller所有返回
   */
  @Pointcut("execution(* com.example.springboot.controller..*.*(..))")
  public void excuseService() {
  }


  /**
   * 将返回参数dapeng.com全部换成dapeng.cn
   *
   * @param proceedingJoinPoint
   * @return
   * @throws Throwable
   */
  @Around("excuseService()")
  public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    //返回参数
    Object result = proceedingJoinPoint.proceed();
    try {
      if (result instanceof ResponseEntity) {
        ResponseEntity responseEntity = (ResponseEntity) result;
        //返回dto类型
        Class clazz = responseEntity.getBody().getClass();

        //获取返回dto
        String json = JSONObject.toJSONString(responseEntity.getBody(),
            SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
        //将.com替换成.cn
        json = json.replaceAll("DO_MAIN_REGEX", "DapengConst.DO_MAIN_REPLACEMENT");

        //将参数重新放入ResponseEntity
        MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return new ResponseEntity(JSONObject.parseObject(json, clazz), responseEntity.getHeaders(),
            responseEntity.getStatusCode());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
}
