package com.example.springboot.config;/*
package com.example.springboot.config;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import java.lang.reflect.WildcardType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

*/
/**
 * @progrm:TestSpringBoot
 * @Description:
 * @Author: leichengxu
 * @Date:2020-07-14 11:26
 *//*
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
　　　　　.apis(RequestHandlerSelectors.basePackage("com.example"))
        .paths(PathSelectors.any())
        .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
            .title("Spring Boot————Swagger2构建Restful APIs")
            .description("消息中心使用")
            .termsOfServiceUrl("https://swagger.io")
            .version("1.0")
            .build();
    }
}

*/


import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import java.lang.reflect.WildcardType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ProjectName: springboot-demo
 * @since: JDK-1.8
 * @author: dapeng-liguoqing
 * @create: 2020-06-09  15:26:02
 * @version: v1.0
 * @description: Swagger2配置类
 **/
@Profile("!prod")
@EnableSwagger2
@Configuration
public class Swagger2Config {
  @Autowired
  private TypeResolver typeResolver;


  /**
   * 内容功能模块
   */
  @Bean
  public Docket contentApi() {
    return newDocket(new ApiInfoBuilder()
        .title("功能区")
        .description("CES")
        .contact(new Contact("LCX", "http://www.mesos.top", "1192919554@qq.com"))
        .version("1")
        .build(), "com.example.springboot.controller");
  }

  private Docket newDocket(ApiInfo apiInfo, String basePackage) {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName(apiInfo.getTitle())
        .apiInfo(apiInfo)
        .select()
        .apis(Predicates.and(basePackage(basePackage), withClassAnnotation(Api.class)))
        .paths(PathSelectors.any())
        .build()
        .ignoredParameterTypes(Pageable.class)
        .directModelSubstitute(LocalDate.class, String.class)
        .directModelSubstitute(LocalDateTime.class, String.class)
        .genericModelSubstitutes(ResponseEntity.class)
        .alternateTypeRules(
            newRule(typeResolver.resolve(DeferredResult.class,
                typeResolver.resolve(ResponseEntity.class, WildcardType.class)
                ),
                typeResolver.resolve(WildcardType.class))
        )
        .useDefaultResponseMessages(false)
        .forCodeGeneration(true);
  }

}
