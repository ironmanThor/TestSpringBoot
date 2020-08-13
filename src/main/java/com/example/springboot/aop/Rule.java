package com.example.springboot.aop;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Rule {

 // @TableField(value = "ruleId")
  private int ruleId;
//  @TableField(value = "ruleName")
  private String ruleName;
 // @TableField(value = "perRuleId")
  private int perRuleId;
  private String url;
 // @TableField(value = "picPath")
  private String picPath;

}
