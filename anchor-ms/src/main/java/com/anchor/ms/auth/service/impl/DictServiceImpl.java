package com.anchor.ms.auth.service.impl;

import com.anchor.core.common.base.BaseMapper;
import com.anchor.core.common.base.BaseServiceImpl;
import com.anchor.ms.auth.service.IDictService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.anchor.ms.auth.mapper.DictMapper;
import com.anchor.ms.auth.model.Dict;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: DictServiceImpl
 * @Description: 字典
 * @author xuqh
 * @date 2017-11-13 18:10:30
 * @since version 1.0
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<Dict,Long> implements IDictService{

	@Autowired
	private DictMapper dictMapper;

	@Resource(name="dictMapper")
	public void setBaseMapper(BaseMapper baseMapper) {
		this.baseMapper = baseMapper;
	}

	@Override
	public List<Dict> getDictMapList() {
		List<Dict> list =  dictMapper.getDictMapList();
		list.forEach(d->{
			d.setList(DictItemServiceImpl.createDictItemTree(d.getList()));
		});
		return list;
	}


}