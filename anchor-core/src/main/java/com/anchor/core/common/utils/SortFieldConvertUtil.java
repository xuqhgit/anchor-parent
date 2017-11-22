package com.anchor.core.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuqh
 * @ClassName: SortOrderConvertUtil
 * @Description: 排序字段转换 防止前端字段
 * @date 2017/10/11 14:44
 * @since 1.0.1
 */
public class SortFieldConvertUtil {
    private final static Map<String,String> sortMap = new HashMap<String,String>(32);
    static{
        sortMap.put("rank","rank");
        sortMap.put("state","state");
    }
    public static String  getSortField(String field){
        return sortMap.get(field);
    }

    public static void setSortField(String field,String dbField){
        sortMap.put(field,dbField);
    }

}
