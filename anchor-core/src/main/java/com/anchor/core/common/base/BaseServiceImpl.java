package com.anchor.core.common.base;

import com.anchor.core.common.query.QueryPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author apple
 * @ClassName: BaseServiceImpl
 * @Description:
 * @date 2017/5/14 16:07
 * @since version 1.0
 */
public abstract class BaseServiceImpl<T extends BaseModel,PK extends Serializable> implements BaseService<T,PK>{

    protected BaseMapper<T,PK> baseMapper;


    public void insert(T t) {
        baseMapper.insert(t);
    }

    public int update(T t) {
        return baseMapper.update(t);
    }

    public T get(PK pk){
        return baseMapper.get(pk);
    }

    public int delete(PK pk) {
        return baseMapper.delete(pk);
    }

    public List<T> getList() {
        return baseMapper.getList();
    }

    public <D> List<D> getListByPage(QueryPage queryPage) {
        return baseMapper.getListByPage(queryPage);
    }

    public <D>PageInfo<D> getPageInfo(QueryPage queryPage){
        PageHelper.startPage(queryPage.getPageNum(), queryPage.getPageSize());
        List<D> list = getListByPage(queryPage);
        return new PageInfo<D>(list);
    }
}
