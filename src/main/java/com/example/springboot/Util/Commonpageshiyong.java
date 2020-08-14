/*
package com.example.springboot.Util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dapeng.fierce.aggregate.common.stereotype.Constants;
import com.dapeng.fierce.aggregate.controller.course.model.StageDto;
import com.dapeng.fierce.aggregate.controller.course.model.StageQuery;
import com.dapeng.fierce.aggregate.dao.CourseChannelMapper;
import com.dapeng.fierce.aggregate.dao.UserMapper;
import com.dapeng.fierce.aggregate.dao.modle.AdminStageDo;
import com.dapeng.fierce.aggregate.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

*/
/**
 * @author szz
 *//*

@Api(tags = "期pai")
@Validated
@RestController
@RequestMapping("/b2b")
public class Commonpageshiyong {

    @Autowired
    private CourseChannelMapper courseChannelMapper;

    @Autowired
    private UserMapper userMapper;


    @ApiOperation(value = "根据条件查询期列表")
    @GetMapping("/courses/{id}/stages")
    public ResponseEntity getStages(@Validated StageQuery query,
                                    @PathVariable(value = "id") @NotBlank(message = "课程id不能为空") String id){
    ---    CommonExample commonExample=new CommonExample();
    ---    CommonExample.Criteria criteria=commonExample.or();
    ---    criteria.singleValue("cc.course_id =",id);
    ---    criteria.singleValue("cc.is_deleted =",false);
        if(!StringUtils.isEmpty(query.getLiveChannelId())){
            criteria.singleValue("cc.live_channel_id =",query.getLiveChannelId());
        }
        if(Objects.nonNull(query.getStartDate())){
            criteria.singleValue("cc.service_start_date >=",query.getStartDate());
        }
        if(Objects.nonNull(query.getEndDate())){
            criteria.singleValue("cc.service_end_date <=",query.getEndDate());
        }
        if(StringUtils.isNotBlank(query.getTitle())){
            criteria.singleValue("cc.title like",query.getTitle()+"%");
        }
        if(StringUtils.isNotEmpty(query.getSort())){
            commonExample.setOrderByClause("cc.service_start_date "+query.getSort()+"");
        } else{
            commonExample.setOrderByClause("cc.service_start_date desc");
        }
        int totalCount = courseChannelMapper.selectAdminStageListCount(commonExample, query.getTimeStatus(),query.getTeacherNickName());

    ---    PageUtil pageUtil=new PageUtil(query.getPage(),query.getSize(),totalCount);
    ---    commonExample.setStart(pageUtil.getOffset());
    ---    commonExample.setEnd(pageUtil.getLimit());
    ---    List<AdminStageDo> adminStageDos = courseChannelMapper.selectAdminStageList(commonExample,query.getTimeStatus(),query.getTeacherNickName());
        List<StageDto> stages = adminStageDos.stream().map(s ->{
            StageDto stageDto = AdminStageDo.AdminStageConvert.INSTANCE.doToDto(s);
            LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.select(User::getNickname);
            queryWrapper.inSql(User::getUserId, String.format("SELECT user_id FROM course_instructor WHERE course_id = '%s' AND  course_channel_id = '%s'",id,s.getId()));
            List<String> teacherNickNames = userMapper.selectList(queryWrapper).stream().map(User::getNickname).collect(Collectors.toList());
            stageDto.setTeacherNickNames(teacherNickNames);
            return stageDto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok()
                .header(Constants.PAGINATION_COUNT_HEADER, String.valueOf(pageUtil.getTotalSize()))
                .header(Constants.PAGINATION_PAGES_HEADER, String.valueOf(pageUtil.getTotalPage()))
                .header(Constants.PAGINATION_SIZE_HEADER, String.valueOf(pageUtil.getPageSize()))
                .header(Constants.PAGINATION_NUMBER_HEADER, String.valueOf(pageUtil.getCurrPage()))
                .body(stages);
    }

}
*/
