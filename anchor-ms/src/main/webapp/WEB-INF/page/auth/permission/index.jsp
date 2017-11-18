<%@ page contentType="text/html;charset=UTF-8" import="com.anchor.ms.auth.model.Permission" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>管理</title>
    <%@include file="../../common.jsp" %>
    <%--<link rel="stylesheet" href="/static/plugins/jquery-treegrid/css/jquery.treegrid.css">--%>
</head>
<body>


<div class="container-fluid ">
    <h3>列表</h3>

    <div class="row-fluid ">
        <div class="span12 search-form">
            <form id="form" class="form-horizontal" role="form">
                <div class="form-group">

                    <label class="col-sm-1 control-label" for="code">权限编码：</label>
                    <div class="col-sm-2">
                        <input class="form-control" id="code" name="code" type="text" placeholder="权限编码"/>
                    </div>

                    <label class="col-sm-1 control-label" for="name">名称：</label>
                    <div class="col-sm-2">
                        <input class="form-control" id="name" name="name" type="text" placeholder="名称"/>
                    </div>

                    <label class="col-sm-1 control-label" for="type">类型：</label>
                    <div class="col-sm-2">
                        <input class="form-control" id="type" name="type" type="text" placeholder="类型"/>
                    </div>

                </div>
                <div class="form-group">
                    <label class="col-sm-1 control-label" >状态：</label>
                    <div class="col-sm-2">
                        <select id="permissionStatus" class="form-control" name="status"></select>
                    </div>

                    <div class="col-sm-1">
                        <button type="button" class="btn btn-primary" id="search"
                                data-toggle="button"><span class="glyphicon glyphicon-search"></span>搜索
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <div class="span12">
            <div id="toolbar" class="btn-group">
                <shiro:hasPermission name="auth:permission:add:index">
                    <a role="button" id="addPermission" class="btn btn-default">
                        <i class="glyphicon glyphicon-plus"></i>添加
                    </a>
                </shiro:hasPermission>

                <shiro:hasPermission name="auth:permission:deleteBatch">
                    <a href="#addPermissionModal" role="button" class="btn btn-default" id="deleteBatchButton">
                        <i class="glyphicon glyphicon-trash"></i>批量删除
                    </a>
                </shiro:hasPermission>
            </div>

            <table id="permissionTree"></table>
        </div>
    </div>
</div>


<%@include file="../../common_script.jsp" %>
<script src="/static/bootstrap/ext/table/ext/treegrid.js"></script>
<%--<script src="/static/plugins/jquery-treegrid/js/jquery.treegrid.bootstrap3.js"></script>--%>
<script>

    var bt;
    var permissionValidConfig = {

        code: {
            rule: {
                codeValid: true
            },
            message: {
                required: '必填项'
            }
        },
        name: {
            rule: {

                nameValid: true
            },
            message: {}
        },
        url: {
            rule: {
                required: false,
                urlValid: true
            },
            message: {}
        }
    };
    var targetTypeDict
    $(function () {
        targetTypeDict = anchor.getDict("TARGET_TYPE");
        var statusDict = anchor.getDict("STATUS");
        anchor.createFromGroupSelect("permissionStatus",statusDict,"",['','==选择状态==']);
        jQuery.validator.addMethod("codeValid", function (value, element) {
            var p =/<%=Permission.CODE_PATTERN%>/;
            return p.test(value);
        }, "<%=Permission.CODE_PATTERN_MESSAGE%>");
        jQuery.validator.addMethod("nameValid", function (value, element) {
            var p =/<%=Permission.NAME_PATTERN%>/;
            return p.test(value);
        }, "<%=Permission.NAME_PATTERN_MESSAGE%>");
        jQuery.validator.addMethod("urlValid", function (value, element) {
            if (value) {
                var p =/<%=Permission.URL_PATTERN%>/;
                return p.test(value);
            }
            return true;
        }, "<%=Permission.URL_PATTERN_MESSAGE%>");

        var params = {
            url: '/permission/treegrid',
            treeView: true,
            treeId: "id",
            treeField: "name",
            treeRootLevel: 1,
            clickToSelect: true,
            queryParams: function (params) {
                var temp = {
                    pageSize: 13,   //页面大小
                    pageNum: 0,  //页码
                    sort: params.sort,  //排序列名
                    sortOrder: params.order//排位命令（desc，asc）
                };
                return $.extend(temp, $('#form').serializeObject());
            },
            columns: [
                {
                    title: '名称', field: 'name', align: 'left', width: '150', formatter: function (index, row) {
                    var opts = row['name'] + "<div style='float:right;margin-right: 5px;'>";
                    <shiro:hasPermission name="auth:permission:add:index">
                    opts += " <a href='javascript:void(0);' class='glyphicon glyphicon-plus' onclick=\"addPermission(\'" + row.id + "\')\"></a> ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="auth:permission:edit:index">
                    opts += " <a href='javascript:void(0);' class='glyphicon glyphicon-pencil' onclick=\"editPermission(\'" + row.id + "\',\'" + row.pid + "\')\"></a> ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="auth:permission:delete">
                    opts += " <a href='javascript:void(0);' class='glyphicon glyphicon-trash' onclick=\"deletePermission(\'" + row.id + "\')\"></a> ";
                    </shiro:hasPermission>
                    opts += "</div>";
                    return opts
                }
                },
                {title: '编码', field: 'code', align: 'left', width: '120'},
                {title: '路径', field: 'url', align: 'left', width: '120'},
                {title: '排序', field: 'rank', align: 'center', width: '50'},
                {title: 'Target', field: 'targetType', align: 'center', width: '50', formatter: function (index, row) {
                        return anchor.getDictItemTextByValue(targetTypeDict.list, row.targetType);
                    }
                },
                {
                    title: '状态', field: 'status', align: 'center', width: '50', formatter: function (index, row) {
                    return anchor.getDictItemTextByValue(statusDict.list, row.status);
                }
                },
                {
                    title: '图标', field: 'icon', align: 'left', width: '80', formatter: function (index, row) {
                    if (!row.icon)return "";
                    return "<i class='" + row.icon + "'></i>" + row.icon;
                }
                }
            ]
        };
        bt = anchor.bootstrapTable("permissionTree", params);
        $('#search').click(function () {
            bt.bootstrapTable('refresh');
        });
        //回车事件绑定
        document.onkeydown = function (event) {
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if (e && e.keyCode == 13) {
                $('#search').click();
            }
        };

        $('#addPermission').click(function () {
            addPermission(null);
        });

        /**
         * 批量删除操作
         *
         */
        $('#deleteBatchButton').click(function () {

            var ids = $.map(bt.bootstrapTable('getSelections'), function (row) {
                return row.id;
            });
            if (ids.length == 0) {
                anchor.alert("请选择删除数据");
                return;
            }
            anchor.confirm("确定要删除【" + ids.length + "】条数据么？", function () {
                anchor.request("/permission/deleteBatch", {ids: ids}, function (data) {
                    bt.bootstrapTable('refresh');
                }, null);
            });

        });

    });
    /**
     * 详情
     */
    function detailPermission(permissionId) {
        $.dialog({
            title: '',
            content: 'url:/permission/edit/' + permissionId,
            type: 'blue',
            columnClass: 'medium',
            draggable: true,
            onContentReady: function () {

            }
        });
    }
    /**
     * 删除
     */
    function deletePermission(permissionId) {
        anchor.confirm("确认要删除该用户么?", function () {
            anchor.request("/permission/delete/" + permissionId, {}, function (data) {
                anchor.alert(data.message);
                bt.bootstrapTable('refresh');
            }, null);
        });
    }
    /**
     * 编辑
     */
    function editPermission(permissionId, pid) {
        var editFormId = "editPermissionForm";
        var dialog = $.dialog({
            title: '',
            content: 'url:/permission/edit/' + permissionId,
            columnClass: 'medium',
            draggable: true,
            onContentReady: function () {
                anchor.createFromGroupSelect(editFormId+" select[name=targetType]",targetTypeDict);
                var validateConfig = anchor.validFieldConfig(permissionValidConfig, anchor.formField(editFormId));
                validateConfig['id'] = editFormId;
                var valid = anchor.validate(validateConfig);
                $('#editPermission').click(function () {
                    if (valid.form()) {
                        anchor.request("/permission/edit", $('#' + editFormId).serializeObject(), function (data) {
                            if (pid && pid != 'null') {
                                bt.bootstrapTable('loadNodeChild', pid);
                            }
                            else {
                                bt.bootstrapTable('refresh');
                            }
                            anchor.alert("保存成功");
                            dialog.close();
                        }, null);
                    }
                });
            }
        });
    }
    function addPermission(pid) {
        var url = "/permission/add" + (pid ? "?pid=" + pid : "");
        var addFormId = "addPermissionForm";
        var addDialog = $.dialog({
            title: '',
            content: 'url:' + url,
            columnClass: 'medium',
            draggable: true,
            onContentReady: function () {
                anchor.createFromGroupSelect(addFormId+" select[name=targetType]",targetTypeDict);
                var validateConfig = anchor.validFieldConfig(permissionValidConfig, anchor.formField(addFormId));
                validateConfig['id'] = addFormId;
                var valid = anchor.validate(validateConfig);
                $('#savePermission').click(function () {
                    if (valid.form()) {
                        <shiro:hasPermission name="auth:permission:add">
                        anchor.request("/permission/add", $('#' + addFormId).serializeObject(), function (data) {
                            if (data.code == 1) {
                                if (!pid) {
                                    bt.bootstrapTable('refresh');
                                }
                                else {
                                    bt.bootstrapTable('loadNodeChild', pid);
                                }
                                anchor.alert("保存成功");
                                addDialog.close();
                            }
                            else {
                                anchor.alert(data.message);
                            }
                        }, null);
                        </shiro:hasPermission>

                    }
                });
            }

        });
    }
</script>
</body>
</html>