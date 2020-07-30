package com.example.springboot.controller;

import com.example.springboot.aop.Log;
import com.example.springboot.client.UserBaseClientDto;
import com.example.springboot.client.UserClient;
import com.example.springboot.dto.UserBaseDto;
import com.example.springboot.dto.UserBaseDto.UserBaseDtoMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-07-10 14:03
 */
@RestController
@Api(tags = "用户模块")
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserClient userClient;

  @ApiOperation("查询用户信息")
  @Log(operationType = "用户")
  @GetMapping()
  public ResponseEntity<List<UserBaseDto>> getUserLists(@RequestParam List<String> userIds){
    ResponseEntity<List<UserBaseClientDto>> r = userClient.getUserBranch(userIds);
    List<UserBaseDto> userBaseDtos = UserBaseDtoMapper.MAPPER.from(r.getBody());
    return  ResponseEntity.ok(userBaseDtos);
  }
}
