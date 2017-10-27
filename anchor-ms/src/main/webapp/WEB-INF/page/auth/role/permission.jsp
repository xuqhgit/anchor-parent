<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">【${role.name}】权限设置</h3>
        </div>
        <div id="procitytree"></div>
        <script>
            $(function(){
                function handleRows(rows){
                    rows.forEach(function(val,index){
                        val['text'] = val.name;
                        val['nodes'] = val.child;
                        val['icon'] = undefined;
                        val["state"] = {};
                        val["state"]["checked"] = true;
                        val["state"]["expanded"] = true;
                        val["state"]["selected"] = false;
                        handleRows(val.child);
                    });
                }
                anchor.request("/permission/tree",{},function(data){
                    handleRows(data.rows);
                    var tree = data.rows;
                    var $this = $("#procitytree").treeview({
                        data : tree,// 赋值
                        highlightSelected : false,// 选中项不高亮，避免和上述制定的颜色变化规则冲突
                        multiSelect : true,// 不允许多选，因为我们要通过check框来控制
                        showCheckbox : true,// 展示checkbox
                        onNodeChecked : function(event, node) {


                            // 省级节点被选中，那么市级节点都要选中
                            if (node.nodes != null) {
                                $.each(node.nodes, function(index, value) {
                                    $this.treeview('checkNode', value.nodeId, {
                                        silent : true
                                    });
                                });
                            } else {
                                // 市级节点选中的时候，要根据情况判断父节点是否要全部选中

                                // 父节点
                                var parentNode = $this.treeview('getParent', node.nodeId);

                                var isAllchecked = true; // 是否全部选中

                                // 当前市级节点的所有兄弟节点，也就是获取省下面的所有市
                                var siblings = $this.treeview('getSiblings', node.nodeId);
                                for ( var i in siblings) {
                                    // 有一个没选中，则不是全选
                                    if (!siblings[i].state.checked) {
                                        isAllchecked = false;
                                        break;
                                    }
                                }

                                // 全选，则打钩
                                if (isAllchecked) {
                                    $this.treeview('checkNode', parentNode.nodeId, {
                                        silent : true
                                    });
                                } else {// 非全选，则变红
                                    $this.treeview('selectNode', parentNode.nodeId, {
                                        silent : true
                                    });
                                }
                            }
                        },
                        onNodeUnchecked : function(event, node) {

                            var silentByChild = false;
                            // 选中的是省级节点
                            if (node.nodes != null) {
                                // 这里需要控制，判断是否是因为市级节点引起的父节点被取消选中
                                // 如果是，则只管取消父节点就行了
                                // 如果不是，则子节点需要被取消选中
                                if (silentByChild) {
                                    $.each(node.nodes, function(index, value) {
                                        $this.treeview('uncheckNode', value.nodeId, {
                                            silent : true
                                        });
                                    });
                                }
                            } else {
                                // 市级节点被取消选中

                                var parentNode = $this.treeview('getParent', node.nodeId);

                                var isAllUnchecked = true; // 是否全部取消选中

                                // 市级节点有一个选中，那么就不是全部取消选中
                                var siblings = $this.treeview('getSiblings', node.nodeId);
                                for ( var i in siblings) {
                                    if (siblings[i].state.checked) {
                                        isAllUnchecked = false;
                                        break;
                                    }
                                }

                                // 全部取消选中，那么省级节点恢复到默认状态
                                if (isAllUnchecked) {
                                    $this.treeview('unselectNode', parentNode.nodeId, {
                                        silent : true,
                                    });
                                    $this.treeview('uncheckNode', parentNode.nodeId, {
                                        silent : true,
                                    });
                                } else {
                                    silentByChild = false;
                                    $this.treeview('selectNode', parentNode.nodeId, {
                                        silent : true,
                                    });
                                    $this.treeview('uncheckNode', parentNode.nodeId, {
                                        silent : true,
                                    });
                                }
                            }
                            silentByChild = true;
                        },
                    });
                    $this.treeview('expandAll', {// 节点展开
                        silent : false
                    });
                });
            });
        </script>
    </div>
</div>

