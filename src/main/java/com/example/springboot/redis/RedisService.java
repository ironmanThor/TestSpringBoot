package com.example.springboot.redis;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * redis操作类.
 *
 * @author zhangliangrui
 */
@Component
public class RedisService {

  @Autowired
  private RedisTemplate redisTemplate;

  /**
   * 释放缓存链接
   */
  public void unbindConnection() {
    RedisConnectionUtils
        .unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
  }

  /**
   * 【指定缓存失效时间 2017年12月20日14:54:58 qiaoliang】
   */
  public boolean expire(String key, long time) {
    try {
      if (time > 0) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【根据key 获取过期时间 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key 键 不能为null
   * @return 时间(秒) 返回0代表为永久有效
   */
  public long getExpire(String key) {
    return redisTemplate.getExpire(key, TimeUnit.SECONDS);
  }

  /**
   * 【判断key是否存在 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key 键
   * @return true 存在 false不存在
   */
  public boolean isKey(String key) {
    try {
      return redisTemplate.hasKey(key);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【删除缓存 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key 可以传一个值 或多个
   */
  public Integer delete(String... key) {
    if (key != null && key.length > 0) {
      try {
        if (key.length == 1) {
          Boolean deleteResult = redisTemplate.delete(key[0]);
          if (deleteResult) {
            return 1;
          }
        } else {
          Long deleteResult = redisTemplate.delete(CollectionUtils.arrayToList(key));
          return deleteResult.intValue();
        }
      } finally {
        unbindConnection();
      }
    }
    return 0;
  }


  /**
   * 【普通缓存获取 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key 键
   * @return 值
   */
  public Object get(String key) {
    try {
      return key == null ? null : redisTemplate.opsForValue().get(key);
    } finally {
      unbindConnection();
    }
  }


  /**
   * 【普通缓存放入 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param value 值
   * @return true成功 false失败
   */
  public boolean set(String key, Object value) {
    try {
      redisTemplate.opsForValue().set(key, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      unbindConnection();
    }

  }

  /**
   * 【普通缓存放入并设置时间 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param value 值
   * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
   * @return true成功 false 失败
   */
  public boolean set(String key, long time, Object value) {
    try {
      if (time > 0) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
      } else {
        set(key, value);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【递增 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param delta 要增加几(大于0)
   */
  public long incr(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("递增因子必须大于0");
    }
    try {
      return redisTemplate.opsForValue().increment(key, delta);
    } finally {
      unbindConnection();
    }
  }


  /**
   * 【递减 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param delta 要减几(大于0)
   */
  public long decr(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("递减因子必须大于0");
    }
    try {
      return redisTemplate.opsForValue().increment(key, -delta);
    } finally {
      unbindConnection();
    }
  }


  /**
   * 【HashGet 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key  键 不能为null
   * @param item 项 不能为null
   * @return 值
   */
  public Object hashGet(String key, String item) {
    try {
      return redisTemplate.opsForHash().get(key, item);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【获取hashKey对应的所有键值 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key 键
   * @return 对应的多个键值
   */

  public Map<Object, Object> hashGet(String key) {
    try {
      return redisTemplate.opsForHash().entries(key);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【HashSet 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key 键
   * @param map 对应多个键值
   * @return true 成功 false 失败
   */
  public boolean hashsSet(String key, Map<String, Object> map) {
    try {
      redisTemplate.opsForHash().putAll(key, map);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      unbindConnection();
    }
  }


  /**
   * 【HashSet 并设置时间 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key  键
   * @param map  对应多个键值
   * @param time 时间(秒)
   * @return true成功 false失败
   */
  public boolean hashsSet(String key, Map<String, Object> map, long time) {
    try {
      redisTemplate.opsForHash().putAll(key, map);
      if (time > 0) {
        expire(key, time * 60);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      unbindConnection();
    }
  }


  /**
   * 【向一张hash表中放入数据,如果不存在将创建 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param item  项
   * @param value 值
   * @return true 成功 false失败
   */
  public boolean hashsSet(String key, String item, Object value) {
    try {
      redisTemplate.opsForHash().put(key, item, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      unbindConnection();
    }
  }

  public Set<Object> getKeys(String key) {
    try {
      return redisTemplate.keys(key);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 获取指定变量中的hashMap值
   */
  public List<Object> hashGetToList(String key) {
    try {
      return redisTemplate.opsForHash().values(key);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【向一张hash表中放入数据,如果不存在将创建 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param item  项
   * @param value 值
   * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
   * @return true 成功 false失败
   */
  public boolean hashsSet(String key, String item, Object value, long time) {
    try {
      redisTemplate.opsForHash().put(key, item, value);
      if (time > 0) {
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      unbindConnection();
    }
  }


  /**
   * 【删除hash表中的值 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key  键 不能为null
   * @param item 项 可以使多个 不能为null
   */
  public void delectHash(String key, Object... item) {
    try {
      redisTemplate.opsForHash().delete(key, item);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【判断hash表中是否有该项的值 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key  键 不能为null
   * @param item 项 不能为null
   * @return true 存在 false不存在
   */
  public boolean isHashKey(String key, String item) {
    try {
      return redisTemplate.opsForHash().hasKey(key, item);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【hash递增 如果不存在,就会创建一个 并把新增后的值返回 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key  键
   * @param item 项
   * @param by   要增加几(大于0)
   */
  public double hashIncr(String key, String item, double by) {
    try {
      return redisTemplate.opsForHash().increment(key, item, by);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【hash递减 并把新增后的值返回 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key  键
   * @param item 项
   * @param by   要减少记(小于0)
   */
  public double hashDecr(String key, String item, double by) {
    try {
      return redisTemplate.opsForHash().increment(key, item, -by);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【根据key获取Set中的所有值 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key 键
   */
  public Set<Object> setGet(String key) {
    try {
      return redisTemplate.opsForSet().members(key);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【根据value从一个set中查询,是否存在 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param value 值
   * @return true 存在 false不存在
   */
  public boolean setIsKey(String key, Object value) {
    try {
      return redisTemplate.opsForSet().isMember(key, value);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【将数据放入set缓存 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key    键
   * @param values 值 可以是多个
   * @return 成功个数
   */
  public long setSet(String key, Object... values) {
    try {
      return redisTemplate.opsForSet().add(key, values);
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【将set数据放入缓存 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key    键
   * @param time   时间(秒)
   * @param values 值 可以是多个
   * @return 成功个数
   */
  public long setSetAndTime(String key, long time, Object... values) {
    try {
      Long count = redisTemplate.opsForSet().add(key, values);
      if (time > 0) {
        expire(key, time * 60);
      }
      return count;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【获取set缓存的长度 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key 键
   */
  public long setGetSetSize(String key) {
    try {
      return redisTemplate.opsForSet().size(key);
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【移除值为value的 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key    键
   * @param values 值 可以是多个
   * @return 移除的个数
   */
  public long setRemove(String key, Object... values) {
    try {
      return redisTemplate.opsForSet().remove(key, values);
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【获取list缓存的内容 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param start 开始
   * @param end   结束  0 到 -1代表所有值
   */
  public List<Object> listGet(String key, long start, long end) {
    try {
      return redisTemplate.opsForList().range(key, start, end);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【获取list缓存的长度 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key 键
   */
  public long listGetListSize(String key) {
    try {
      return redisTemplate.opsForList().size(key);
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【通过索引 获取list中的值 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
   */
  public Object listGetIndex(String key, long index) {
    try {
      return redisTemplate.opsForList().index(key, index);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【将list放入缓存 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param value 值
   */
  public boolean listSet(String key, Object value) {
    try {
      redisTemplate.opsForList().rightPush(key, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【将list放入缓存 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param value 值
   * @param time  时间(秒)
   */
  public boolean listSet(String key, Object value, long time) {
    try {
      redisTemplate.opsForList().rightPush(key, value);
      if (time > 0) {
        expire(key, time * 60);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【将list放入缓存 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param value 值
   */
  public boolean listSet(String key, List<Object> value) {
    try {
      redisTemplate.opsForList().rightPushAll(key, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【将list放入缓存 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param value 值
   * @param time  时间(秒)
   */
  public boolean listSet(String key, List<Object> value, long time) {
    try {
      redisTemplate.opsForList().rightPushAll(key, value);
      if (time > 0) {
        expire(key, time * 60);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【根据索引修改list中的某条数据 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param index 索引
   * @param value 值
   */
  public boolean listUpdateIndex(String key, long index, Object value) {
    try {
      redisTemplate.opsForList().set(key, index, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【移除N个值为value 2017年12月20日14:54:58 qiaoliang】
   *
   * @param key   键
   * @param count 移除多少个
   * @param value 值
   * @return 移除的个数
   */
  public long listRemove(String key, long count, Object value) {
    try {
      Long remove = redisTemplate.opsForList().remove(key, count, value);
      return remove;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    } finally {
      unbindConnection();
    }
  }

  /**
   * <h4>功能：[redis的有序集合][2018/9/14 16:38][创建人：xiaoxiang_hou]</h4>
   * <h4></h4>
   */
  public void zSetAdd(String key, Object object, Double score) {
    try {
      redisTemplate.opsForZSet().add(key, object, score);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      unbindConnection();
    }
  }

  public void zSetAdd(String key, Set<ZSetOperations.TypedTuple<Object>> tuples) {
    try {
      redisTemplate.opsForZSet().add(key, tuples);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      unbindConnection();
    }
  }

  /**
   * <h4>功能：[redis的有序集合][2018/9/14 16:38][创建人：xiaoxiang_hou]</h4>
   * <h4></h4>
   */
  public Long zSetCount(String key, Double min, Double max) {
    try {
      return redisTemplate.opsForZSet().count(key, min, max);
    } catch (Exception e) {
      e.printStackTrace();
      return 0L;
    } finally {
      unbindConnection();
    }
  }

  /**
   * <h4>功能：[zset集合的数量][2018/9/18 15:33][创建人：xiaoxiang_hou]</h4>
   * <h4></h4>
   */
  public Long zSetListSize(String key) {
    try {
      return redisTemplate.opsForZSet().size(key);
    } catch (Exception e) {
      e.printStackTrace();
      return 0L;
    } finally {
      unbindConnection();
    }
  }

  /**
   * <h4>功能：[redis的有序集合从大到小获取][2018/9/14 16:38][创建人：xiaoxiang_hou]</h4>
   * <h4></h4>
   */
  public Set<Object> zSetGetList(String key, int min, int max) {
    try {
      return redisTemplate.opsForZSet().reverseRange(key, min, max);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      unbindConnection();
    }
  }

  public boolean zSetRemove(String key, Object... values) {
    try {
      return redisTemplate.opsForZSet().remove(key, values) > 0L;
    } finally {
      unbindConnection();
    }
  }
}