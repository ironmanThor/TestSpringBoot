package com.example.springboot.client;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-07-10 14:04
 */
@FeignClient(name = "userClient",url = "${dapeng.teaching.user-api}")
@Component
public interface UserClient {
  /**
   * 根据用户id集合查询用户集合
   *
   * @param userIds :用户id集合
   * @return 用户集合
   * @author qifengs
   * @date 2020/5/15
   **/
  @GetMapping("/b2b/manage/users")
  ResponseEntity<List<UserBaseClientDto>> getUserBranch(@RequestParam List<String> userIds);

  @Slf4j
  @Component
  class UserClientHystrix implements  UserClient{

    @Override
    public ResponseEntity<List<UserBaseClientDto>> getUserBranch(List<String> userIds) {
      log.info("熔断");
      return ResponseEntity.ok(Lists.newArrayList());
    }
  }
}
