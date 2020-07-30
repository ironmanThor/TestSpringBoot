package com.example.springboot.client;

import java.util.Date;
import lombok.Data;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-06-16 15:22
 */
@Data
public class CourseClientDto {

  private String id;
  private String title;
  private String type;

  private String status;

  private CollegeBean college;
  private Integer ordered;
  private String createUserId;
  private Date lastModifyTime;
  private Boolean recommended;
  private Integer totalStudent;

  @Data
  public static class CollegeBean {
    private String id;
    private String name;

  }
}
