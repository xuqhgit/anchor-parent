package com.anchor.ms.auth.mapper;


import com.anchor.core.common.base.BaseMapper;
import com.anchor.ms.auth.model.User;
import org.apache.ibatis.annotations.Param;


/**
 * @ClassName: UserMapper
 * @Description: 
 * @author anchor
 * @date 2017-05-14 19:25:09
 * @since version 1.0
 */
public interface UserMapper extends BaseMapper<User,Long> {


    /**
     * 获取用户
     *
     * @author xuqh
     * @date 2017/9/29 9:45
     * @param username
     * @return
     */
    public User findUserByUsername(String username);

    /**
     * 登录
     *
     * @author xuqh
     * @date 2017/9/29 9:45
     * @param username
     * @param password
     * @return
     */
    public User login(@Param("username") String username,@Param("password") String password);
}