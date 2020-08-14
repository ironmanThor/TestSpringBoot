package com.example.springboot.client;

import com.example.springboot.client.CourseClient.CourseClientHystrix;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @progrm:TestSpringBoot
 * @Description:调用课程微服务接口
 * @Author: leichengxu
 * @Date:2020-06-16 14:58
 */
@FeignClient(name = "CourseClient", url = "${dapeng.operation.requestUrl}/course-api", fallback = CourseClientHystrix.class)
public interface CourseClient {

  @GetMapping("/b2b/manage/courses")
  ResponseEntity<List<CourseClientDto>> getCourseList(@SpringQueryMap CourseClientQuery courseClientQuery);


  @Slf4j
  @Component
  class CourseClientHystrix implements CourseClient {

    @Override
    public ResponseEntity<List<CourseClientDto>> getCourseList(
        CourseClientQuery courseClientQuery) {
      return ResponseEntity.ok(Lists.newArrayList());
    }
  }
}
