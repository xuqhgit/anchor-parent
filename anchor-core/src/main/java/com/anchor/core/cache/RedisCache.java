package com.anchor.core.cache;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xuqh
 * @ClassName: RedisCache
 * @Description:
 * @date 2017/10/16 16:14
 * @since 1.0.1
 */
public class RedisCache {

    private  RedisTemplate<Serializable, Serializable> redisTemplate;


    public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 添加获取Redis模板
     *
     * @author xuqh
     * @date 2017/9/9 15:19
     * @return
     */
    private  RedisTemplate<Serializable, Serializable> getRedis(){
        return redisTemplate;
    }
    public  final Serializable get(final String key) {
        //expire(key, EXPIRE);
        return getRedis().opsForValue().get(key);
    }


    public  final void set(final String key, final Serializable value) {
        getRedis().boundValueOps(key).set(value);
        //expire(key, EXPIRE);
    }
    public  final void set(final String key, final Serializable value,long expire) {
        getRedis().boundValueOps(key).set(value,expire,TimeUnit.MILLISECONDS);
        //expire(key, EXPIRE);
    }
    public final void set(final String key, final Serializable value,long expire,final int index){
         getRedis().execute(new RedisCallback<Void>() {
             @Override
             public Void doInRedis(RedisConnection connection) throws DataAccessException {
                 connection.select(index);
                 byte[] rawValue = ((RedisSerializer<Serializable>) getRedis().getValueSerializer()).serialize(value);
                 connection.set(key.getBytes(),rawValue, Expiration.milliseconds(expire), RedisStringCommands.SetOption.upsert());
                 return null;
             }
         });
    }

    /**
     * 判断是否存在
     * @param key
     * @return
     */
    public  final Boolean exists(final String key) {

        return getRedis().hasKey(key);
    }

    /**
     * 删除键
     * @param key
     */
    public  final void del(final String... key) {
        getRedis().delete(key);
    }


    public  final void delAll(final String pattern) {
        getRedis().delete(getRedis().keys(pattern));
    }

    public  final String type(final String key) {

        return getRedis().type(key).getClass().getName();
    }

    /**
     * 在某段时间后失效
     *
     * @return
     */
    public  final Boolean expire(final String key, final int seconds) {
        return getRedis().expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 在某个时间点失效
     *
     * @param key
     * @param unixTime
     * @return
     */
    public  final Boolean expireAt(final String key, final long unixTime) {
        return getRedis().expireAt(key, new Date(unixTime));
    }

    public  final Long ttl(final String key) {
        return getRedis().getExpire(key, TimeUnit.SECONDS);
    }

    public  final void setrange(final String key, final long offset, final String value) {

        getRedis().boundValueOps(key).set(value, offset);
    }

    public  final List<Serializable> getRange(final String key, final long startOffset, final long endOffset) {

        return getRedis().boundListOps(key).range(startOffset, endOffset);
    }

    /**
     * 获取set集合
     * @param key
     * @return
     */
    public final Set<Serializable> sget(final String key){
        return getRedis().boundSetOps(key).members();
    }

    /**
     * 添加set
     * @param key
     * @param values
     * @return
     */
    public final Long sadd(final String key, Serializable... values){
       return getRedis().boundSetOps(key).add(values);
    }

    /**
     * 获取set 大小
     * @param key
     * @return
     */
    public final Long scard(final String key){
        return getRedis().boundSetOps(key).size();
    }

    /**
     * set 移除元素
     * @param key
     * @param values
     * @return
     */
    public final Long srem(final String key ,Object...values){
        return getRedis().boundSetOps(key).remove(values);
    }

    /**
     * 随机一个set里面值
     * @param key
     * @param <T>
     * @return
     */
    public final <T>T srandmember(final String key){
        return (T)getRedis().boundSetOps(key).randomMember();
    }

    /**
     * 右加入数据
     * @param key
     * @param value
     * @return
     */
    public final Long rpush(final String key,Serializable value){
        return getRedis().boundListOps(key).rightPush(value);
    }

    /**
     * 获取list 长度
     * @param key
     * @return
     */
    public final Long llen(final String key){
        return getRedis().boundListOps(key).size();
    }

    public  final Serializable getSet(final String key, final String value) {
        return getRedis().boundValueOps(key).getAndSet(value);
    }

    /** 递增 */
    public  Long incr(final String redisKey) {

        return getRedis().execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incr(redisKey.getBytes());
            }
        });
    }
    public  boolean setNX(final String redisKey,final String value){
        return getRedis().execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] rawValue = ((RedisSerializer<Serializable>) getRedis().getValueSerializer()).serialize(value);
                return connection.setNX(redisKey.getBytes(), rawValue);
            }
        });
    }

    /**
     * hget获取 指定key 指定字段的值
     * @author xuqh
     * @date 2017-6-7 下午7:52:52
     * @param rediskey
     * @param fieldKey
     * @return
     */
    public  <T> T hget(final String rediskey,String fieldKey){
        return (T)getRedis().boundHashOps(rediskey).get(fieldKey);
    }

    /**
     * 添加批量添加数据
     *
     * @author xuqh
     * @date 2017/9/9 18:18
     * @param map
     * @return
     */
    public  List<Object> batchLpush(final Map<Serializable,List<Object>> map){
        return getRedis().executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<Serializable> keySerializer = ( RedisSerializer<Serializable>)getRedis().getKeySerializer();
                RedisSerializer<Serializable> valueSerializer = ( RedisSerializer<Serializable>)getRedis().getValueSerializer();
                Iterator<Map.Entry<Serializable,List<Object>>> iterator = map.entrySet().iterator();

                while (iterator.hasNext()){
                    Map.Entry<Serializable,List<Object>> entry = iterator.next();
                    byte[][] arr = new byte[entry.getValue().size()][];

                    List<Object> list = entry.getValue();
                    for(int i=0;i<list.size();i++){
                        arr[i]=valueSerializer.serialize((Serializable) list.get(i));
                    }
                    byte[] key = keySerializer.serialize(entry.getKey());
                    connection.del(key);
                    if(list.size()>0){
                        connection.lPush(key ,arr);
                    }

                }
                return null;
            }
        });
    }

    /**
     * 左获取列表
     *
     * @author xuqh
     * @date 2017/9/22 18:09
     * @param key
     * @param start
     * @param end
     * @return
     */
    public  <T>  List<T> lrange(final String key ,final long start,final long end){
        return (List<T>)getRedis().boundListOps(key).range(start,end);
    }


    /**
     * 添加批量添加数据
     *
     * @author xuqh
     * @date 2017/9/9 18:18
     * @param map
     * @return
     */
    public  List<Object> batchSet(final Map<Serializable,Serializable> map){
        return getRedis().executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<Serializable> keySerializer = ( RedisSerializer<Serializable>)getRedis().getKeySerializer();
                RedisSerializer<Serializable> valueSerializer = ( RedisSerializer<Serializable>)getRedis().getValueSerializer();
                Iterator<Map.Entry<Serializable,Serializable>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<Serializable,Serializable> entry = iterator.next();
                    connection.set( keySerializer.serialize(entry.getKey()),valueSerializer.serialize(entry.getValue()));
                }
                return null;
            }
        });
    }


    /**
     * 添加批量添加数据
     *
     * @author xuqh
     * @date 2017/9/9 18:18
     * @param map
     * @return
     */
    public  List<Object> batchExpire(final Map<Serializable,Long> map){
        return getRedis().executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<Serializable> keySerializer = ( RedisSerializer<Serializable>)getRedis().getKeySerializer();
                Iterator<Map.Entry<Serializable,Long>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<Serializable,Long> entry = iterator.next();
                    connection.expire( keySerializer.serialize(entry.getKey()),entry.getValue());
                }
                return null;
            }
        });
    }
    /**
     * 根据正则获取多个keys
     *
     * @param pattern
     * @return
     */
    public  Set<Serializable> keys(Serializable pattern){
        return getRedis().keys(pattern);
    }

    /**
     * 获取多个KEY数据
     *
     * @param keys
     * @param <T>
     * @return
     */
    public  <T> List<T> mget(Collection<Serializable> keys){

        return (List<T>)getRedis().opsForValue().multiGet(keys);
    }
    /**
     * 获取所有值
     * @author xuqh
     * @date 2017-6-7 下午7:53:33
     * @param rediskey
     * @return
     */
    public  <K,V> Map<K,V> hgetAll(final String rediskey){
        BoundHashOperations<Serializable, K, V> opt = getRedis().boundHashOps(rediskey);
        return opt.entries();
    }
    /**
     * 设置全部值
     * @author xuqh
     * @date 2017-6-7 下午7:57:02
     * @param rediskey
     * @param value
     */
    public  void  hsetAll(final String rediskey,Map<? extends Object, ? extends Object> value){
        getRedis().boundHashOps(rediskey).putAll(value);
    }
    /**
     * 设置单个值
     * @author xuqh
     * @date 2017-6-7 下午7:57:21
     * @param rediskey
     * @param fieldKey
     * @param value
     */
    public  void hset(final String rediskey,final String fieldKey,final Serializable value){
        getRedis().boundHashOps(rediskey).put(fieldKey, value);
    }

    /**
     * 删除指定的数据
     * @author xuqh
     * @date 2017-6-13 下午12:27:18
     * @param rediskey
     * @param fieldKeys
     */
    public  void hdel(final String rediskey,final String... fieldKeys){
        getRedis().boundHashOps(rediskey).delete(fieldKeys);
    }

    /**
     * 根据field keys 获取集合
     * @author xuqh
     * @date 2017-6-9 上午11:22:45
     * @param key
     * @param fieldKeys
     * @return
     */
    public  <T,K> List<T> hmget(final String key,final List<K> fieldKeys){
        BoundHashOperations<Serializable, K, T> opt = getRedis().boundHashOps(key);
        return opt.multiGet(fieldKeys);
    }

    public  <T> List<T> hvals(final String rediskey){
        BoundHashOperations<Serializable, Object, T> opt = getRedis().boundHashOps(rediskey);
        return opt.values();
    }

    /** 增 */
    public  Double incrBy(final String redisKey, final Double value) {
        return getRedis().execute(new RedisCallback<Double>() {
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incrBy(redisKey.getBytes(), value);
            }
        });
    }

    /** 减 */
    public  Double decrBy(final String redisKey, final Double value) {
        return getRedis().execute(new RedisCallback<Double>() {
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incrBy(redisKey.getBytes(), value*-1);
            }
        });
    }
}
