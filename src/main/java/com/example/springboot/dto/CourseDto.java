package com.example.springboot.dto;

import com.example.springboot.client.CourseClientDto;
import com.example.springboot.datafill.UserDataFill;
import com.example.springboot.datafill.annonation.DataFill;
import com.example.springboot.datafill.annonation.DataFillEnable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-06-16 15:23
 */
@Data
@DataFillEnable
public class CourseDto {
  private String id;
  private String title;
  private String type;
  private String status;
  private CourseClientDto.CollegeBean college;
  private Integer ordered;
  @DataFill(value = "createUserId",handler = UserDataFill.class)
  private UserBaseDto userBaseDto;
  @JsonIgnore
  private String createUserId;
  private Date lastModifyTime;
  private Boolean recommended;
  private Integer totalStudent;

  @Data
  public static class CollegeBean {
    private String id;
    private String name;

  }

  @Mapper
  public interface CourseDtoMapper{
    CourseDtoMapper MAPPER= Mappers.getMapper(CourseDtoMapper.class);

    CourseDto from(CourseClientDto courseClientDto);
    List<CourseDto> from(List<CourseClientDto> courseClientDtos);
  }
}
