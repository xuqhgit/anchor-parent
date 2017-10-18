package com.anchor.ms.core.shiro.cache;

import com.anchor.core.cache.RedisCache;
import com.anchor.core.common.ext.PackagesSqlSessionFactoryBean;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @email json@sojson.com
 */

public class RedisShiroCache implements Cache<Serializable, Serializable> {

    /**
     * 为了不和其他的缓存混淆，采用追加前缀方式以作区分
     */
    private static final String REDIS_SHIRO_CACHE = "shiro-cache:";
    /**
     * Redis 分片(分区)，也可以在配置文件中配置
     */
    private static final int DB_INDEX = 1;

    private RedisCache redisCache;

    private String name;


    static final Class<RedisShiroCache> SELF = RedisShiroCache.class;

    public RedisShiroCache(String name, RedisCache redisCache) {
        this.name = name;
        this.redisCache = redisCache;
    }

    /**
     * 自定义relm中的授权/认证的类名加上授权/认证英文名字
     */
    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Serializable get(Serializable key) throws CacheException {

        return  redisCache.get(buildCacheKey(key));
    }

    public Serializable put(Serializable key, Serializable value) throws CacheException {
        Serializable previos = get(key);

        redisCache.set(buildCacheKey(key), value);

        return previos;
    }

    public Serializable remove(Serializable key) throws CacheException {
        Serializable previos = get(key);
        redisCache.del(buildCacheKey(key));
        return previos;
    }


    public void clear() throws CacheException {
        //TODO--
    }


    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }


    public Set<Serializable> keys() {

        return null;
    }


    public Collection<Serializable> values() {
        //TODO
        return null;
    }

    private String buildCacheKey(Object key) {
        return REDIS_SHIRO_CACHE + getName() + ":" + key;
    }

}
