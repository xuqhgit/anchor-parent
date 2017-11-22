<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">【${role.name}】权限设置</h3>
        </div>
        <div class="box-body">
            <div id="rolePermissionTree">

            </div>
        </div>
        <div class="box-footer">

            <a id="saveRolePermission" class="btn btn-info pull-right">保存</a>
        </div>
        <script>
            $(function () {
                function handleRows(rows, level) {
                    var lev = level + 1;
                    rows.forEach(function (val, index) {
                        val['text'] = val.name;
                        val['nodes'] = val.children;
                        val['icon'] = undefined;
                        val['level'] = lev;
                        val["state"] = {};
                        val["state"]["checked"] = val.checked;
                        handleRows(val.children, lev);
                    });
                }

                anchor.request("/permission/tree/${role.id}", {}, function (data) {
                    handleRows(data.rows, 0);
                    var tree = data.rows;
                    var optionLevel = -1;
                    var $this = $("#rolePermissionTree").treeview({
                        data: tree,// 赋值
                        highlightSelected: false,// 选中项不高亮，避免和上述制定的颜色变化规则冲突
                        multiSelect: true,// 不允许多选，因为我们要通过check框来控制
                        showCheckbox: true,// 展示checkbox
                        onNodeChecked: function (event, node) {
                            //当前节点选中的时候，子节点选中
                            if (optionLevel == -1) {
                                optionLevel = node.level;
                            }
                            if (optionLevel <= node.level) {
                                $.each(node.nodes, function (index, value) {
                                    if (value.state && !value.state.checked) {
                                        $this.treeview('checkNode', value.nodeId, {
                                            silent: true
                                        });
                                    }
                                });
                            }
                            // 当前节点选中的时候，父节点选中
                            if (optionLevel >= node.level) {
                                var parentNode = $this.treeview('getParent', node.nodeId);
                                if (parentNode.state && !parentNode.state.checked) {
                                    $this.treeview('checkNode', parentNode.nodeId, {
                                        silent: true
                                    });
                                }
                            }

                            if (optionLevel == node.level) {
                                optionLevel = -1;
                            }

                        },
                        onNodeUnchecked: function (event, node) {
                            if (optionLevel == -1) {
                                optionLevel = node.level;
                            }
                            if (optionLevel <= node.level) {
                                //子不选中
                                $.each(node.nodes, function (index, value) {
                                    if (value.state && value.state.checked) {
                                        $this.treeview('uncheckNode', value.nodeId, {
                                            silent: true
                                        });
                                    }

                                });

                            }
//                            if(optionLevel>=node.level){
//                                //父节点判断是否不选中
//                                var siblings = $this.treeview('getSiblings', node.nodeId);
//                                var isUncheckParentNode = true;
//                                if(siblings&&siblings.length>0){
//                                    for(var i=0;i<siblings.length;i++){
//                                        if(siblings[i].state.checked){
//                                            isUncheckParentNode = false;
//                                            break;
//                                        }
//                                    }
//                                    if(isUncheckParentNode){
//                                        var parentNode = $this.treeview('getParent', siblings[0].nodeId);
//                                        if(parentNode.state&&parentNode.state.checked){
//                                            $this.treeview('uncheckNode',parentNode.nodeId, {
//                                                silent : true
//                                            });
//                                        }
//
//                                    }
//                                }
//                            }
                            if (optionLevel == node.level) {
                                optionLevel = -1;
                            }

                        }
                    }).treeview('expandAll', {// 节点展开
                        silent: false
                    });

                    $("#saveRolePermission").click(function () {
                        var permissionIds = $this.treeview("getChecked").map(function (node, index) {
                            return node.id;
                        });
                        anchor.request("/role/permission/${role.id}", {"permissionIds": permissionIds}, function (data) {
                            if (data.code == 1) {
                                anchor.alert("保存成功,共 " + data.data + " 条");
                            }
                            else {
                                anchor.alert(data.message);
                            }
                        });
                    });
                });
            });
        </script>
    </div>
</div>

