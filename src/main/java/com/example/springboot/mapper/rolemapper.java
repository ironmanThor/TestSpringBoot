package com.example.springboot.mapper;


import com.example.springboot.aop.Role;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


public interface rolemapper{

  List<Role> getroles();

}
