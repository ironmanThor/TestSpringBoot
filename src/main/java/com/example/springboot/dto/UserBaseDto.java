package com.example.springboot.dto;


import com.example.springboot.client.UserBaseClientDto;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author：zhangliangrui
 * @Description：统一的用户model
 * @Date：19-9-25
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBaseDto implements Serializable {

  @ApiModelProperty(value = "用户ID")
  private String userId;

  @ApiModelProperty(value = "昵称")
  private String nickname;

  @ApiModelProperty(value = "头像")
  private String avatar;


  @Mapper
  public interface UserBaseDtoMapper {

    UserBaseDtoMapper MAPPER = Mappers.getMapper(UserBaseDtoMapper.class);

    UserBaseDto from(UserBaseClientDto dto);

    List<UserBaseDto> from(List<UserBaseClientDto> dto);

  }
}
