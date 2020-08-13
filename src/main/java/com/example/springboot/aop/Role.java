package com.example.springboot.aop;

import lombok.Data;

@Data
public class Role {

  private int roleId;
  private String roleName;
  private String createBy;
  private java.sql.Timestamp modifyDate;
  private String status;
  private String ruleList;


}
