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
        Map<String,List<ITree>> map = new HashMap<>((int)Math.ceil(list.size()/0.75)+1);
        Set<String> nodeIds = new HashSet<>();
        LinkedList<String> existNodeId = new LinkedList<>();

        list.stream().forEach(p->{
            List<ITree> child = map.get(p.getIdString());
            if(child==null){
                child = p.getChildTree();
                map.put(p.getIdString(),child);
            }
            if(p.getChildTree().size()==0&& CollectionUtils.isNotEmpty(child)){
                p.setChildTree(child);
            }
            nodeIds.remove(p.getIdString());
            existNodeId.add(p.getIdString());
            if(p.getPidString()==null){
                resultList.add(p);
            }
            else{
                List<ITree> parentChild = map.get(p.getPidString());
                if(parentChild==null){
                    parentChild = new LinkedList<>();
                    map.put(p.getPidString(), parentChild);
                    if(!existNodeId.contains(p.getPidString())){
                        nodeIds.add(p.getPidString());
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
