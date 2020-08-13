package com.example.springboot.controller;

import com.example.springboot.aop.Log;
import com.example.springboot.aop.Role;
import com.example.springboot.client.CourseClient;
import com.example.springboot.client.CourseClientDto;
import com.example.springboot.client.CourseClientQuery;
import com.example.springboot.client.CourseClientQuery.CourseClientQueryMapper;
import com.example.springboot.dto.CourseDto;
import com.example.springboot.dto.CourseDto.CourseDtoMapper;
import com.example.springboot.dto.CourseQuery;
import com.example.springboot.mapper.rolemapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @progrm:TestSpringBoot
 * @Description:课程控制器
 * @Author: leichengxu
 * @Date:2020-06-16 15:10
 */
@RestController
@Api(tags = "课程模块")
@RequestMapping("/admin/courses")
public class CourseController {
  @Autowired
  private   CourseClient courseClient;

  @Autowired
  private com.example.springboot.mapper.rolemapper rolemapper;

  @ApiOperation("查询课程管理列表")
  @Log(operationType = "cesada")
  @GetMapping()
  public ResponseEntity<List<CourseDto>> getCourseList(CourseQuery courseQuery){
    CourseClientQuery courseClientQuery = CourseClientQueryMapper.MAPPER.from(courseQuery);
    ResponseEntity<List<CourseClientDto>> responseEntity = courseClient
        .getCourseList(courseClientQuery);
    return ResponseEntity.status(responseEntity.getStatusCode())
        .headers(responseEntity.getHeaders()).body(CourseDtoMapper.MAPPER.from(responseEntity.getBody()));
  }

  @ApiOperation("查询角色")
  @GetMapping("/role")
  public List<Role> roles(){
      return  rolemapper.getroles();
  }



}
