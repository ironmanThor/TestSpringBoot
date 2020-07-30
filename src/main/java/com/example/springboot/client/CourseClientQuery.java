package com.example.springboot.client;

import com.example.springboot.dto.CourseQuery;
import java.util.List;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-06-16 15:06
 */
@Data
public class CourseClientQuery {


  private String collegeId;

  private String status;

  private String type;

  private String title;

  private Boolean recommended;

  private Integer page;

  private Integer size;

  @Mapper
  public interface CourseClientQueryMapper{
    CourseClientQueryMapper MAPPER = Mappers.getMapper(CourseClientQueryMapper.class);
    @Mappings({
        @Mapping(source = "name",target = "title")
    })
    CourseClientQuery from(CourseQuery courseQuery);
    List<CourseClientQuery> from(List<CourseQuery> courseQueries);
  }
}
