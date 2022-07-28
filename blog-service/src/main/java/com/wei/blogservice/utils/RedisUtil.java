package com.wei.blogservice.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.db.nosql.redis.RedisDS;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * @author 凌雪
 */
@Component
public class RedisUtil {

    @Resource
    RedisDS redisDS;

    public static final long NOT_EXIST = -2L;
    public static final long INFINITE = -1L;

    public <T> T getObject(String key, Class<T> tClass){
        try (Jedis jedis = redisDS.getJedis()) {
            byte[] bytes = jedis.get(key.getBytes());
            return JsonUtil.toObject(bytes, tClass);
        }
    }

    public void del(String key){
        try (Jedis jedis = redisDS.getJedis()) {
            jedis.del(key.getBytes());
        }
    }

    public void set( String key, Object o){
        try (Jedis jedis = redisDS.getJedis()) {
            jedis.set(key.getBytes(), JsonUtil.toBytes(o));
        }
    }

    public boolean containKey(String key){
        try (Jedis jedis = redisDS.getJedis()) {
            Long ttl = jedis.ttl(key.getBytes());
            return ttl != NOT_EXIST;
        }
    }

    public void setex(String key, Object o, long time){
        try (Jedis jedis = redisDS.getJedis()) {
            jedis.setex(key.getBytes(), time, JsonUtil.toBytes(o));
        }
    }

    public void psetex(String key, Object o, long time){
        try (Jedis jedis = redisDS.getJedis()) {
            jedis.psetex(key.getBytes(), time, JsonUtil.toBytes(o));
        }
    }

    public <T> void update(String key, T newObj, Class<T> tClass){
        try (Jedis jedis = redisDS.getJedis()) {
            //-2，key不存在 -1无限期 >0剩余时间单位(ms)
            Long ttl = jedis.pttl(key);
            if (ttl == NOT_EXIST) {
                return;
            }
            //从redis读取数据
            byte[] bytes = jedis.get(key.getBytes());
            T oldObj = JsonUtil.toObject(bytes, tClass);
            //更新数据
            BeanUtil.copyProperties(newObj, oldObj, CopyOptions.create().ignoreNullValue());
            //存入redis
            bytes = JsonUtil.toBytes(oldObj);
            if (ttl == INFINITE) {
                jedis.set(key.getBytes(), bytes);
            } else {
                jedis.psetex(key.getBytes(), ttl, bytes);
            }
        }

    }
}
