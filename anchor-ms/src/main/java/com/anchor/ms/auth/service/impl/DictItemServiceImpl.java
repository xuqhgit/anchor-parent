package com.anchor.ms.auth.service.impl;

import com.anchor.core.common.base.BaseMapper;
import com.anchor.core.common.base.BaseServiceImpl;
import com.anchor.core.common.query.QueryPage;
import com.anchor.core.common.tree.TreeUtil;
import com.anchor.ms.auth.mapper.DictItemMapper;
import com.anchor.ms.auth.model.DictItem;
import com.anchor.ms.auth.model.DictItemTree;
import com.anchor.ms.auth.model.PermissionTree;
import com.anchor.ms.auth.service.IDictItemService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DictItemServiceImpl
 * @Description: 字典元素
 * @author xuqh
 * @date 2017-11-13 18:10:30
 * @since version 1.0
 */
@Service
public class DictItemServiceImpl extends BaseServiceImpl<DictItem,Long> implements IDictItemService {

	@Autowired
	private DictItemMapper dictItemMapper;

	@Resource(name="dictItemMapper")
	public void setBaseMapper(BaseMapper baseMapper) {
		this.baseMapper = baseMapper;
	}

	@Override
	public List<DictItemTree> getDictItemTree(QueryPage<DictItem> queryPage) {
		List list = dictItemMapper.getDictItemTree(queryPage);
		return TreeUtil.assembleTree(list);
	}
}