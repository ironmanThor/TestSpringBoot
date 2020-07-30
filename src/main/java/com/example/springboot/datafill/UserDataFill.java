package com.example.springboot.datafill;

import com.example.springboot.client.UserBaseClientDto;
import com.example.springboot.client.UserClient;
import com.example.springboot.controller.UserController;
import com.example.springboot.datafill.handler.AbstractDataFillHandler;
import com.example.springboot.datafill.handler.DataFillHandler;
import com.example.springboot.datafill.metadata.DataFillMetadata;
import com.example.springboot.dto.UserBaseDto;
import com.example.springboot.dto.UserBaseDto.UserBaseDtoMapper;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-07-14 09:46
 */
@Component
public class UserDataFill extends AbstractDataFillHandler {

  @Autowired
  private UserClient userClient;

  @Override
  public DataFillHandler fill(DataFillMetadata metadata) throws Exception {
    ResponseEntity<List<UserBaseClientDto>> responseEntity = userClient
        .getUserBranch(Lists.newArrayList(metadata.getSelectionKey().toString()));
    List<UserBaseDto> userBaseDtos = UserBaseDtoMapper.MAPPER.from(responseEntity.getBody());
    userBaseDtos.stream().filter(userBaseDto -> Objects.equals(userBaseDto.getUserId(),metadata.getSelectionKey()))
        .findFirst().ifPresent(userBaseDto -> {
          metadata.setFillObj(userBaseDto);
    });
    return null;
  }
}
