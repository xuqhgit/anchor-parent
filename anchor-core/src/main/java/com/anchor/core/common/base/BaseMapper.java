package com.anchor.core.common.base;

import com.anchor.core.common.dto.QueryFilter;

import java.io.Serializable;
import java.util.List;

/**
 * @author apple
 * @ClassName: BaseMapper
 * @Description:
 * @date 2017/5/14 16:07
 * @since version 1.0
 */
public interface BaseMapper<T extends BaseModel,PK extends Serializable> {


    /**
     * 添加实例
     * @param t
     */
    public void insert(T t);

    /**
     * 更新实例
     * @param t
     * @return
     */
    public int update(T t);


    /**
     * 获取实例
     * @param pk
     * @return
     */
    public T get(PK pk);


    /**
     * 删除实例
     * @param pk
     * @return
     */
    public int delete(PK pk);

    /**
     * 获取列表
     * @return
     */
    public List<T> getList();


    /**
     * 根据过滤器 获取数据列表
     * @param queryFilter
     * @return
     */
    public List<T> getListByFilter(QueryFilter queryFilter);

}
