package com.example.springboot.dto;

import com.example.springboot.EnumZidingyi.CourseStatus;
import com.example.springboot.EnumZidingyi.EnumTest;
import lombok.Data;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-06-16 15:07
 */
@Data
public class CourseQuery {

  private String collegeId;

  @EnumTest(message = "课程类型错误",enumClass = CourseStatus.class)
  private String status;

  private String type;

  private String name;

  private Boolean recommended;

  private Integer page;

  private Integer size;



}
