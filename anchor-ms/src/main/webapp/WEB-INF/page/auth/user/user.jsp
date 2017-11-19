<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>用户管理</title>
    <%@include file="../../common.jsp" %>
</head>
<body>


<div class="container-fluid ">
    <h3>用户列表</h3>

    <div class="row-fluid ">
        <div class="span12 search-form">
            <form id="form" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="col-xs-1 control-label" for="username">用户名：</label>
                    <div class="col-sm-2">
                        <input class="form-control" id="username" name="username" type="text" placeholder="用户名"/>
                    </div>
                    <label class="col-xs-1 control-label" for="name">名称：</label>
                    <div class="col-sm-2">
                        <input class="form-control" id="name" name="name" type="text" placeholder="名称"/>
                    </div>
                    <label class="col-xs-1 control-label" >状态：</label>
                    <div class="col-sm-2">
                        <select id="userStatus" class="form-control" name="status"></select>
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
                <shiro:hasPermission name="auth:user:add:index">
                    <a role="button" id="addUser" class="btn btn-default">
                        <i class="glyphicon glyphicon-plus"></i>添加用户
                    </a>
                </shiro:hasPermission>

                <%--<button type="button" class="btn btn-default" data-toggle="modal">--%>
                <%--<i class="glyphicon glyphicon-plus">添加用户</i>--%>
                <%--</button>--%>
                <shiro:hasPermission name="auth:user:deleteBatch">
                    <a href="#addUserModal" role="button" class="btn btn-default" id="deleteBatchButton">
                        <i class="glyphicon glyphicon-trash"></i>批量删除
                    </a>
                </shiro:hasPermission>
            </div>
            <table id="userTable"></table>
        </div>
    </div>
</div>


<%@include file="../../common_script.jsp" %>
<script>
    var bt;
    var userValidConfig = {
        name: {
            rule: {
                required: true
            },
            message: {
                required: '请输入名称'
            }
        },
        username: {
            rule: {
                required: true,
                remote: "/user/usernameExist"
            },
            message: {
                required: '请输入用户名',
                remote: '用户名已存在'
            }
        },
        email: {
            rule: {
                required: true,
                email: true
            },
            message: "请输入一个正确的邮箱",
        }
    };
    $(function () {
        var statusDict = anchor.getDict("STATUS");
        anchor.createFromGroupSelect("userStatus",statusDict,"",['','==选择状态==']);
        var params = {
            url: '/user/grid',
            queryParams: function (params) {
                var temp = {
                    pageSize: params.limit,   //页面大小
                    pageNum: params.offset / params.limit + 1,  //页码
                    sort: params.sort,  //排序列名
                    sortOrder: params.order//排位命令（desc，asc）
                };
                return $.extend(temp, $('#form').serializeObject());
            },
            columns: [
                {checkbox: true},
                {title: 'id', field: 'id', visible: false},
                {title: '用户名', field: 'username', align: 'center', width: '100'},
                {title: '名称', field: 'name', align: 'center', width: '100'},
                {title: '角色', field: 'role.name', align: 'center', width: '80',
                    formatter: function (index, row, value) {
                        if (row.role) {
                            return row.role.name;
                        }
                        return "";
                    }
                },
                { title: '状态',field: 'status',align: 'center',sortable: true,width: '80',
                    formatter: function (index, row, value) {
                        return anchor.getDictItemTextByValue(statusDict.list, row.status);
                    }
                },
                {title: '注册日期', field: 'createTime', align: 'center', sortable: true, width: '120'},
                {title: '邮箱', field: 'email', align: 'center', width: '100'},
                {title: 'QQ', field: 'qq', align: 'center', width: '100'},
                {title: '手机号', field: 'phone', align: 'center', width: '100'},
                {
                    title: '操作', field: 'opt', align: 'center', width: '120', formatter: function (index, row) {
                    var opts = "";
                    <shiro:hasPermission name="auth:user:get">
                    opts += "<a href='javascript:void(0);' class='btn btn-xs' onclick=\"detailUser(\'" + row.id + "\')\">查看</a>|";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="auth:user:edit:index">
                    opts += "<a href='javascript:void(0);' class='btn btn-xs' onclick=\"editUser(\'" + row.id + "\')\">编辑</a>|";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="auth:user:delete">
                    opts += "<a href='javascript:void(0);' class='btn btn-xs' onclick=\"deleteUser(\'" + row.id + "\')\">删除</a>";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="auth:user:setRole">
                    opts += "<a href='javascript:void(0);' class='btn btn-xs' onclick=\"setRole(\'" + row.id + "\')\">设置角色</a>";
                    </shiro:hasPermission>
                    return opts;
                }
                }
            ]
        };
        bt = anchor.bootstrapTable("userTable", params);
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
        loadDictData();
        $('#addUser').click(function () {
            var addFormId = "addUserForm";
            var addDialog = $.dialog({
                title: '',
                content: 'url:/user/add',
                columnClass: 'medium',
                draggable: true,
                onContentReady: function () {
                    var validateConfig = anchor.validFieldConfig(userValidConfig, anchor.formField(addFormId));
                    validateConfig['id'] = addFormId;
                    var valid = anchor.validate(validateConfig);
                    $('#saveUser').click(function () {
                        if (valid.form()) {
                            <shiro:hasPermission name="auth:user:add">
                            anchor.request("/user/add", $('#' + addFormId).serializeObject(), function (data) {
                                if (data.code == 1) {
                                    bt.bootstrapTable('refresh');
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
                anchor.request("/user/deleteBatch", {ids: ids}, function (data) {
                    bt.bootstrapTable('refresh');
                }, null);
            });

        });

    });
    /**
     * 详情
     */
    function detailUser(userId) {
        $.dialog({
            title: '',
            content: 'url:/user/edit/' + userId,
            type: 'blue',
            columnClass: 'medium',
            onContentReady: function () {

            }
        });
    }
    /**
     * 删除
     */
    function deleteUser(userId) {
        anchor.confirm("确认要删除该用户么?", function () {
            anchor.request("/user/delete/" + userId, {}, function (data) {
                anchor.alert(data.message);
                bt.bootstrapTable('refresh');
            }, null);
        });
    }
    /**
     * 编辑
     */
    function editUser(userId) {
        var editFormId = "editUserForm";
        var dialog = $.dialog({
            title: '',
            content: 'url:/user/edit/' + userId,
            columnClass: 'medium',
            onContentReady: function () {
                var validateConfig = anchor.validFieldConfig(userValidConfig, anchor.formField(editFormId));
                validateConfig['id'] = editFormId;
                var valid = anchor.validate(validateConfig);
                $('#editUser').click(function () {
                    if (valid.form()) {
                        anchor.request("/user/edit", $('#' + editFormId).serializeObject(), function (data) {
                            bt.bootstrapTable('refresh');
                            anchor.alert("保存成功");
                            dialog.close();
                        }, null);
                    }
                });
            }
        });
    }

    function setRole(userId) {
        var setRoleFormId = "setRoleForm";
        var dialog = $.dialog({
            title: '',
            content: 'url:/user/setRole/' + userId,
            columnClass: 'medium',
            onContentReady: function () {
                var validateConfig = anchor.validFieldConfig(userValidConfig, anchor.formField(setRoleFormId));
                validateConfig['id'] = setRoleFormId;
                var valid = anchor.validate(validateConfig);
                $('#setRoleUser').click(function () {
                    if (valid.form()) {
                        anchor.request("/user/setRole", $('#' + setRoleFormId).serializeObject(), function (data) {
                            bt.bootstrapTable('refresh');
                            anchor.alert("保存成功");
                            dialog.close();
                        }, null);
                    }
                });
            }
        });
    }
</script>
</body>
</html>
