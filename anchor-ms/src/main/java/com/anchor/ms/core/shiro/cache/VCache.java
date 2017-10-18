package com.anchor.ms.core.shiro.cache;

import com.anchor.core.cache.RedisCache;
import com.anchor.core.common.utils.SpringContextUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 *
 * @version 1.0,2016年6月2日 <br/>
 * 
 */
@SuppressWarnings("unchecked")
public class VCache {


	final static RedisCache redisCache = SpringContextUtil.getBean("redisCache", RedisCache.class);
	private VCache() {}
	
	/**
	 * 简单的Get
	 * @param <T>
	 * @param key
	 * @return
	 */
	public static <T> T get(String key ){
		return (T)redisCache.get(key);
	}
	/**
	 * 简单的set
	 * @param key
	 * @param value
	 */
	public static void set(String key ,Serializable value){
		redisCache.set(key,value);
	}
	/**
	 * 过期时间的
	 * @param key
	 * @param value
	 * @param timer （毫秒）
	 */
	public static void setex(String key, Serializable value, long timer) {
		redisCache.set(key,value,timer);
		
	}
	/**
	 * 
	 * @param <T>
	 * @param mapkey map
	 * @param key	 map里的key
	 * @param requiredType value的泛型类型
	 * @return
	 */
	public static <T> T getVByMap(String mapkey,String key , Class<T> requiredType){
		return redisCache.hget(mapkey,key);
	}
	/**
	 * 
	 * @param mapkey map
	 * @param key	 map里的key
	 * @param value   map里的value
	 */
	public static void setVByMap(String mapkey,String key ,Serializable value){
		redisCache.hset(mapkey,key,value);
	}
	/**
	 * 删除Map里的值
	 * @param mapKey
	 * @param dkey
	 * @return
	 */
	public static Object delByMapKey(String mapKey,String...dkey){
		redisCache.hdel(mapKey,dkey);
		return new Long(0);
	}
	
	/**
	 * 往redis里取set整个集合
	 * 
	 * @param <T>
	 * @param setKey
	 * @param requiredType
	 * @return
	 */
	public static <T> Set<T> getVByList(String setKey,Class<T> requiredType){
		return (Set<T>)redisCache.sget(setKey);
	}
	/**
	 * 获取Set长度
	 * @param setKey
	 * @return
	 */
	public static Long getLenBySet(String setKey){
		return redisCache.scard(setKey);
	}
	/**
	 * 删除Set
	 * @param dkey
	 * @return
	 */
	public static Long delSetByKey(String key,String...dkey){
		if(null == dkey){
			return redisCache.srem(key,dkey);
		}
		redisCache.del(key);
		return  0L;
	}
	/**
	 * 随机 Set 中的一个值
	 * @param key
	 * @return
	 */
	public static String srandmember(String key){
		return redisCache.srandmember(key);
	}
	/**
	 * 往redis里存Set
	 * @param setKey
	 * @param value
	 */
	public static void setVBySet(String setKey,Serializable... value){
		redisCache.sadd(setKey, value);
	}
	/**
	 * 取set 
	 * @param key
	 * @return
	 */
	public static <T>Set<T> getSetByKey(String key){
		return (Set<T>)redisCache.sget(key);
	}
	
	
	/**
	 * 往redis里存List
	 * @param listKey
	 * @param value
	 */
	public static void setVByList(String listKey,Serializable value){
		redisCache.rpush(listKey, value);
	}
	/**
	 * 往redis里取list
	 * 
	 * @param <T>
	 * @param listKey
	 * @param start
	 * @param end
	 * @return
	 */
	public static <T> List<T> getVByList(String listKey,int start,int end){
		return redisCache.lrange(listKey,start,end);
	}
	/**
	 * 获取list长度
	 * @param listKey
	 * @return
	 */
	public static Long getLenByList(String listKey){
		return redisCache.llen(listKey);
	}
	/**
	 * 删除
	 * @param dkey
	 * @return
	 */
	public static void delByKey(String...dkey){
		redisCache.del(dkey);
	}
	/**
	 * 判断是否存在
	 * @param existskey
	 * @return
	 */
	public static boolean exists(String existskey){
		return redisCache.exists(existskey);
	}

}
