package com.example.springboot.aop;

import com.alibaba.fastjson.JSON;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-07-13 16:13
 */
@Aspect
@Component
public class BehaviorLogAspect {

  private static final Logger logger = LoggerFactory.getLogger(BehaviorLogAspect.class);

  @Pointcut("@annotation(com.example.springboot.aop.Log)")
  public void pointcutTest() {
  }


  private String buildReqLog(ProceedingJoinPoint joinPoint) throws Throwable {
    //用于启动目标方法执行的，获取方法的返回值
    Object target = joinPoint.proceed();
    //获取抽象方法
    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    //获取传入点的返回参数
    Object[] args = joinPoint.getArgs();
    //方法返回类名
    StringBuilder builder = new StringBuilder(target.getClass().getName());
    //方法名
    builder.append(method.getName()).append("cesada");
    for (Object arg : args) {
      builder.append(JSON.toJSONString(arg).toString()).append(",");
    }
    logger.info(builder.substring(0, builder.length() - 1));
    return builder.substring(0, builder.length() - 1);
  }

  @Around("pointcutTest()")
  public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
    String reg = null;
    Object res = null;
    //执行接口开始时间
    long start = System.currentTimeMillis();
    try {
      //获取接口返回值
      res = joinPoint.proceed();
      //接口返回类名，方法名，传入参数
      reg = buildReqLog(joinPoint);
      //addOperationLog(joinPoint, res, time);
      return res;
    } catch (Exception e) {
      res = "ERROR";
    } finally {
      long end = System.currentTimeMillis() - start;
      System.out.println(reg + "" + JSON.toJSONString(res) + end);
    }
    return res;
  }


  /*private void addOperationLog(JoinPoint joinPoint, Object res, long time) {
    //传参
    ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    //当前操作人
    String operationUserId = sra.getRequest().getHeader(DapengConst.CURRENT_USER_ID_HEADER);
    //当前连接点签名
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    //获得log注解
    Log annotation = signature.getMethod().getAnnotation(Log.class);
    //添加日志
    BehaviorLog behaviorLog = BehaviorLog.builder()
        .runTime(time)
        .returnValue((String) res)
        .args(JSON.toJSONString(joinPoint.getArgs()))
        .createTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
        .method(signature.getDeclaringTypeName() + "." + signature.getName())
        .extremity(annotation.extremity())
        .operationUnit(annotation.operationUnit().getText())
        .operationType(annotation.operationType().getText())
        .operationUserId(operationUserId)
        .describe(getDetail(signature.getParameterNames(), joinPoint.getArgs(), annotation))
        .build();
    logger.info(JSON.toJSONString(behaviorLog));
  }

  /**
   * 占位符处理,将{{参数名}}换成具体参数值
   *
   * @param argNames :请求参数名
   * @param args :请求参数
   * @param annotation :log注解
   * @return java.lang.String
   * @author baojunz
   * @date 2019/10/15
   **/
  /*private String getDetail(String[] argNames, Object[] args, Log annotation) {
    Map<Object, Object> map = new HashMap<>(4);
    for (int i = 0; i < argNames.length; i++) {
      map.put(argNames[i], args[i]);
    }
    //详情描述
    String detail = annotation.describe();
    try {
      for (Map.Entry<Object, Object> entry : map.entrySet()) {
        Object argName = entry.getKey();
        Object arg = entry.getValue();
        detail = detail.replace("{{" + argName + "}}", JSON.toJSONString(arg));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return detail;
  }*/

}
