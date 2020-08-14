package com.example.springboot.dto;

import com.example.springboot.EnumZidingyi.CourseStatus;
import com.example.springboot.EnumZidingyi.EnumExact;
import com.example.springboot.EnumZidingyi.EnumTest;
import java.util.List;
import lombok.Data;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-14 15:01
 */
@Data
public class CourseVo {

  @EnumExact(message = "测试出现错误",enumClass = CourseStatus.class)
  private String status;
}
