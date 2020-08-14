package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.EnumZidingyi.CourseStatus;
import com.example.springboot.EnumZidingyi.EnumTest;
import com.example.springboot.aop.Log;
import com.example.springboot.aop.Role;
import com.example.springboot.aop.Rule;
import com.example.springboot.client.CourseClient;
import com.example.springboot.client.CourseClientDto;
import com.example.springboot.client.CourseClientQuery;
import com.example.springboot.client.CourseClientQuery.CourseClientQueryMapper;
import com.example.springboot.dto.CourseDto;
import com.example.springboot.dto.CourseDto.CourseDtoMapper;
import com.example.springboot.dto.CourseQuery;
import com.example.springboot.dto.CourseVo;
import com.example.springboot.mapper.rolemapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @Autowired
  private com.example.springboot.mapper.rulemapper rulemapper;

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
  public List<Role> roles(@Validated CourseVo courseVo){
      return  rolemapper.getroles();
  }

  @ApiOperation("查询权限")
  @ApiImplicitParam(value = "角色id",name = "id")
  @GetMapping("/rule/{id}")
  public Rule rules(@PathVariable("id") String id){
    QueryWrapper<Rule> queryWrapper=new QueryWrapper();
    queryWrapper.lambda().eq(Rule::getRuleId,id);
    LambdaQueryWrapper lambdaQueryWrapper= Wrappers.lambdaQuery();
    return  rulemapper.selectOne(queryWrapper);
  }

}
