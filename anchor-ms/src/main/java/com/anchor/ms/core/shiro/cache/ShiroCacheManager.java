package com.anchor.ms.core.shiro.cache;

import org.apache.shiro.cache.Cache;

/**
 *
 * @version 1.0,2016年6月2日 <br/>
 * 
 */
public interface ShiroCacheManager {

    <K, V> Cache<K, V> getCache(String name);

    void destroy();

}
