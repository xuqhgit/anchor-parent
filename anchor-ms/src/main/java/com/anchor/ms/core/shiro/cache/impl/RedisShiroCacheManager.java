package com.anchor.ms.core.shiro.cache.impl;

import com.anchor.core.cache.RedisCache;
import com.anchor.ms.core.shiro.cache.RedisShiroCache;
import com.anchor.ms.core.shiro.cache.ShiroCacheManager;
import org.apache.shiro.cache.Cache;

/**
 * @author xuqh
 * @ClassName: RedisShiroCacheManager
 * @Description:
 * @date 2017/10/16 17:42
 * @since 1.0.1
 */
public class RedisShiroCacheManager implements ShiroCacheManager {

    private RedisCache redisCache;
    public <K, V> Cache<K, V> getCache(String name) {
        return (Cache<K, V>)new RedisShiroCache(name,redisCache);
    }

    public void destroy() {
        //如果和其他系统，或者应用在一起就不能关闭
        //getJedisManager().getJedis().shutdown();
    }

    public RedisCache getRedisCache() {
        return redisCache;
    }

    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }
}
