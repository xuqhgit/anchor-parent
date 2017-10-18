package com.anchor.ms.core.shiro.cache;

import com.anchor.core.cache.RedisCache;
import com.anchor.core.common.ext.PackagesSqlSessionFactoryBean;
import com.anchor.ms.common.utils.LoggerUtils;
import com.anchor.ms.common.utils.SerializeUtil;
import com.anchor.ms.core.shiro.session.CustomSessionManager;
import com.anchor.ms.core.shiro.session.SessionStatus;
import com.anchor.ms.core.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.types.Expiration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Session 管理
 *
 */
@SuppressWarnings("unchecked")
public class RedisShiroSessionRepository implements ShiroSessionRepository {
    public static final String REDIS_SHIRO_SESSION = "anchor:ms:session:";
    //这里有个小BUG，因为Redis使用序列化后，Key反序列化回来发现前面有一段乱码，解决的办法是存储缓存不序列化
    public static final String REDIS_SHIRO_ALL = REDIS_SHIRO_SESSION+":*";
    private static final int SESSION_VAL_TIME_SPAN = 18000;
    private static final int DB_INDEX = 1;

    private RedisCache redisCache;


    public void saveSession(Session session) {
        if (session == null || session.getId() == null)
            throw new NullPointerException("session is empty");
        try {
            //不存在才添加。
            if(null == session.getAttribute(CustomSessionManager.SESSION_STATUS)){
            	//Session 踢出自存存储。
            	SessionStatus sessionStatus = new SessionStatus();
            	session.setAttribute(CustomSessionManager.SESSION_STATUS, sessionStatus);
            }
            //由于session不适合使用json序列化的形式
            redisCache.getRedisTemplate().execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.set(buildRedisSessionKey(session.getId()).getBytes(),SerializeUtil.serialize(session),
                            Expiration.milliseconds(session.getTimeout()), RedisStringCommands.SetOption.UPSERT);
                    return null;
                }
            });

        } catch (Exception e) {
        	LoggerUtils.fmtError(getClass(), e, "save session error，id:[%s]",session.getId());
        }
    }


    public void deleteSession(Serializable id) {
        if (id == null) {
            throw new NullPointerException("session id is empty");
        }
        try {
            redisCache.del(buildRedisSessionKey(id));
        } catch (Exception e) {
        	LoggerUtils.fmtError(getClass(), e, "删除session出现异常，id:[%s]",id);
        }
    }

   

    public Session getSession(Serializable id) {
        if (id == null) throw new NullPointerException("session id is empty");

        return redisCache.getRedisTemplate().execute(new RedisCallback<Session>() {
            @Override
            public Session doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] val = connection.get(buildRedisSessionKey(id).getBytes());
                if(val!=null){
                    return SerializeUtil.deserialize(val,Session.class);
                }
                return null;
            }
        });
    }


    public Collection<Session> getAllSessions() {
        return redisCache.getRedisTemplate().execute(new RedisCallback<List>() {
            @Override
            public List doInRedis(RedisConnection connection) throws DataAccessException {
                Set<byte[]> set = connection.keys(REDIS_SHIRO_ALL.getBytes());
                List<byte[]> vals = connection.mGet( set.toArray(new byte[set.size()][]));
                List<Session> list  = new ArrayList<Session>(vals.size());
                vals.stream().forEach(s->{
                        list.add(SerializeUtil.deserialize(s,Session.class));
                });
                return list;
            }
        });
    }

    private String buildRedisSessionKey(Serializable sessionId) {
        return REDIS_SHIRO_SESSION + sessionId;
    }

    public RedisCache getRedisCache() {
        return redisCache;
    }

    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }
}
