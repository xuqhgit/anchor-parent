package com.anchor.core.common.tree;
/**
 * Created by apple on 2017/11/21.
 */

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author apple
 * @ClassName: TreeUtil
 * @Description:
 * @date 2017/11/21 20:21
 * @since version 1.0
 */
public class TreeUtil {
    public static  <T>List<T> assembleTree(List<ITree> list){
        List<ITree> resultList = new LinkedList<>();
        Map<Object,List<ITree>> map = new HashMap<>((int)Math.ceil(list.size()/0.75)+1);
        ArrayList<Object> nodeIds = new ArrayList<>();
        LinkedList<Object> existNodeId = new LinkedList<>();

        list.stream().forEach(p->{
            List<ITree> child = map.get(p.getId());
            if(child==null){
                child = p.getChildren();
                map.put(p.getId(),child);
            }
            if(p.getChildren().size()==0&& CollectionUtils.isNotEmpty(child)){
                p.setChildren(child);
            }

            nodeIds.remove(p.getId());
            existNodeId.add(p.getId());
            if(p.getPid()==null){
                resultList.add(p);
            }
            else{
                List<ITree> parentChild = map.get(p.getPid());
                if(parentChild==null){
                    parentChild = new LinkedList<>();
                    map.put(p.getPid(), parentChild);
                    if(!existNodeId.contains(p.getPid())){
                        nodeIds.add(p.getPid());
                    }

                }
                parentChild.add(p);
            }
        });
        nodeIds.forEach(n->{
            List<ITree> noParentChild = map.get(n);
            resultList.addAll(noParentChild);
        });
        return (List<T>)resultList;
    }


}
