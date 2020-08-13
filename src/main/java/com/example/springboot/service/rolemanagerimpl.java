package com.example.springboot.service;

import com.example.springboot.aop.Role;
import com.example.springboot.mapper.rolemapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-08-13 16:02
 */
@Service
public class rolemanagerimpl implements rolemanager {
  @Autowired
  private rolemapper rolemapper;

  @Override
  public List<Role> getroles() {
    return rolemapper.getroles();
  }
}
