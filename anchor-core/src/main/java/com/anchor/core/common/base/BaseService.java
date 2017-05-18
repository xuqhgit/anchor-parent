package com.anchor.core.common.base;

import com.anchor.core.common.dto.QueryFilter;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author apple
 * @ClassName: BaseService
 * @Description:
 * @date 2017/5/14 16:07
 * @since version 1.0
 */
public interface BaseService<T extends BaseModel,PK extends Serializable> {
    public void insert(T t);

    public int update(T t);

    public int delete(PK pk);

    public T get(PK pk);

    public List<T> getList();

    public List<T> getListByFilter(QueryFilter queryFilter);

    public PageInfo<T> getPageInfo(QueryFilter queryFilter);

    public void setBaseMapper(BaseMapper baseMapper);

}
