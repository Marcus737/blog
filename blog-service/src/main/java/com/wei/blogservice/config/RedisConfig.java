package com.wei.blogservice.config;

import cn.hutool.db.nosql.redis.RedisDS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author admin
 */
@Configuration
public class RedisConfig {

    /**
     * 全局一个实例就行
     * @return
     */
    @Bean
    public RedisDS getRedisDS(){
        return RedisDS.create();
    }
}
