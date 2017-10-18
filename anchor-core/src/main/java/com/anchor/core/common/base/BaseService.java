package com.anchor.core.common.base;

import com.anchor.core.common.query.QueryPage;
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

    public <D> List<D> getListByPage(QueryPage queryPage);

    public <D>PageInfo<D> getPageInfo(QueryPage queryPage);

    public void setBaseMapper(BaseMapper baseMapper);

}
