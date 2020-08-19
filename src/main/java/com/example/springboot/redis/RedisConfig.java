package com.example.springboot.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Slf4j
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

  /**
   * 实例化 RedisTemplate 对象
   */
  @Bean
  public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);

    // 使用Jackson2JsonRedisSerialize 替换默认序列化
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
        new Jackson2JsonRedisSerializer<>(
            Object.class);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    //过期方法 以activateDefaultTyping代替
    //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

    objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance ,
        ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

    // 设置value的序列化规则和 key的序列化规则
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    redisTemplate.setKeySerializer(new StringRedisSerializer());

    redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

    redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
    redisTemplate.setEnableDefaultSerializer(true);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;

  }

  
  /**
   * 异常处理
   */
  @Override
  @Bean
  public CacheErrorHandler errorHandler() {
    // 异常处理，当Redis发生异常时，打印日志，但是程序正常走
    return new CacheErrorHandler() {
      @Override
      public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        log.error("Redis occur handleCacheGetError:key -> [{}]", key, exception);
      }

      @Override
      public void handleCachePutError(RuntimeException exception, Cache cache, Object key,
          Object value) {
        log.error("Redis occur handleCachePutError:key  -> [{}]", key, exception);
      }

      @Override
      public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        log.error("Redis occur handleCacheEvictError:key -> [{}]", key, exception);
      }

      @Override
      public void handleCacheClearError(RuntimeException exception, Cache cache) {
        log.error("Redis occur handleCacheClearError:", exception);
      }
    };
  }

}
