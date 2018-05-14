<%@ page contentType="text/html;charset=UTF-8" import="com.anchor.ms.auth.model.Dict" language="java" %>
<%@ page import="com.anchor.ms.auth.model.DictItem" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>字典管理</title>
    <%@include file="../../common.jsp" %>
</head>
<body>


<div class="container-fluid ">
    <h3>字典列表</h3>

    <div class="row-fluid ">
        <div class="col-md-5">
            <div class="span12 search-form">
                <form id="form" class="form-inline" role="form">
                    <div class="form-group">
                        <label  for="code">编码：</label>
                        <input class="form-control" id="code" name="code" type="text" placeholder="字典编码"/>
                    </div>
                    <div class="form-group">
                        <label for="name">名称：</label>
                        <input class="form-control" id="name" name="name" type="text" placeholder="名称"/>
                    </div>
                    <div class="form-group">
                        <label  >状态：</label>
                        <select id="dictStatus" class="form-control" name="status"></select>
                    </div>
                    <div class="form-group">
                        <button type="button" class="btn btn-primary" id="search"
                                data-toggle="button"><span class="glyphicon glyphicon-search"></span>搜索
                        </button>
                    </div>
                </form>
            </div>
            <div class="span12">
                <div id="toolbar" class="btn-group">
                    <shiro:hasPermission name="auth:dict:add">
                        <a role="button" id="addDict" class="btn btn-default">
                            <i class="glyphicon glyphicon-plus"></i>添加字典
                        </a>
                    </shiro:hasPermission>


                </div>
                <table id="dictTable"></table>
            </div>
        </div>
        <div class="col-md-7">
            <div class="span12">
                <h3 id="dictItemTitle">字典元素列表</h3>
                <form id="dictItemForm" class="form-horizontal" role="form">
                    <input type="hidden" name="dictId">
                </form>
            </div>
            <div class="span12">
                <div id="dictItemToolbar" class="btn-group">
                    <shiro:hasPermission name="auth:dictItem:add">
                        <a role="button" id="addDictItem" class="btn btn-default">
                            <i class="glyphicon glyphicon-plus"></i>添加字典元素
                        </a>
                    </shiro:hasPermission>
                </div>
                <table id="dictItemTable"></table>
            </div>
        </div>
    </div>
</div>


<%@include file="../../common_script.jsp" %>
<script src="/static/bootstrap/ext/table/ext/treegrid.js"></script>

<script>

    var bt;
    var dictValidConfig = {

        code: {
            rule: {
                required: true,
                codeValid: true
            },
            message: {
                required: '必填项'
            }
        },
        name: {
            rule: {
                required: true,
                nameValid: true
            },
            message: {
                required: '必填项'
            }
        },
    };
    var statusDict;
    $(function () {
        statusDict = anchor.getDict("STATUS");
        anchor.createFromGroupSelect("dictStatus",statusDict,"",['','==选择状态==']);
        jQuery.validator.addMethod("codeValid", function (value, element) {
            var p =/<%=Dict.CODE_PATTERN%>/;
            return p.test(value);
        }, "<%=Dict.CODE_PATTERN_MESSAGE%>");
        jQuery.validator.addMethod("nameValid", function (value, element) {
            var p =/<%=Dict.NAME_PATTERN%>/;
            return p.test(value);
        }, "<%=Dict.NAME_PATTERN_MESSAGE%>");

        var params = {
            url: '/dict/grid',
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
                {title: '字典编码', field: 'code', align: 'center', sortable: true, width: '100'},
                {title: '名称', field: 'name', align: 'center', width: '100'},
                {
                    title: '状态', field: 'status', align: 'center', width: '100', formatter: function (index, row) {
                    return anchor.getDictItemTextByValue(statusDict.list, row.status);
                }},
                {title: '创建时间', field: 'createTime', align: 'center', width: '100'},
                {
                    title: '操作', field: 'opt', align: 'center', width: '120', formatter: function (index, row) {
                    var opts = "";
                    <shiro:hasPermission name="auth:dict:edit">
                    opts += "<a href='javascript:void(0);' class='btn btn-xs' onclick=\"editDict(\'" + row.id + "\')\">编辑</a>";
                    </shiro:hasPermission>
                    opts += "<a href='javascript:void(0);' class='btn btn-xs' onclick=\"dictDetail(\'" + row.id + "\',\'" + row.name + "\',\'" + row.code + "\')\">查看</a>";
                    return opts;
                }
                }
            ]
        };
        bt = anchor.bootstrapTable("dictTable", params);
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

        $('#addDict').click(function () {
            var addFormId = "addDictForm";
            var addDialog = $.dialog({
                title: '',
                content: 'url:/dict/add',
                columnClass: 'medium',
                onContentReady: function () {
                    var validateConfig = anchor.validFieldConfig(dictValidConfig, anchor.formField(addFormId));
                    validateConfig['id'] = addFormId;
                    var valid = anchor.validate(validateConfig);
                    $('#saveDict').click(function () {
                        if (valid.form()) {
                            <shiro:hasPermission name="auth:dict:add">
                            anchor.request("/dict/add", $('#' + addFormId).serializeObject(), function (data) {
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
                anchor.request("/dict/deleteBatch", {ids: ids}, function (data) {
                    bt.bootstrapTable('refresh');
                }, null);
            });

        });

    });


    /**
     * 详情
     */
    function dictDetail(id, name, code) {
        $('#dictItemForm input[name=dictId]').val(id);
        $("#dictItemTitle").text(name + "【" + code + "】");
        dictItemLoad();
    }
    /**
     * 删除
     */
    function deleteDict(dictId) {
        anchor.confirm("确认要删除该用户么?", function () {
            anchor.request("/dict/delete/" + dictId, {}, function (data) {
                anchor.alert(data.message);
                bt.bootstrapTable('refresh');
            }, null);
        });
    }
    /**
     * 编辑
     */
    function editDict(dictId) {
        var editFormId = "editDictForm";
        var dialog = $.dialog({
            title: '',
            content: 'url:/dict/edit/' + dictId,
            columnClass: 'medium',
            onContentReady: function () {
                var validateConfig = anchor.validFieldConfig(dictValidConfig, anchor.formField(editFormId));
                validateConfig['id'] = editFormId;
                var valid = anchor.validate(validateConfig);
                $('#editDict').click(function () {
                    if (valid.form()) {
                        anchor.request("/dict/edit", $('#' + editFormId).serializeObject(), function (data) {
                            bt.bootstrapTable('refresh');
                            anchor.alert("保存成功");
                            dialog.close();
                        }, null);
                    }
                });
            }
        });
    }


    var dictItemBt;
    var dictItemValidConfig = {
        value: {
            rule: {
                required: true,
                valueValid: true
            },
            message: {
                required: '必填项'
            }
        },
        text: {
            rule: {
                required: false,
                textValid: true
            },
            message: {
                required: '必填项'
            }
        }
    };

    $(function () {

        jQuery.validator.addMethod("valueValid", function (value, element) {
            var p =/<%=DictItem.VALUE_PATTERN%>/;
            return p.test(value);
        }, "<%=DictItem.VALUE_PATTERN_MESSAGE%>");
        jQuery.validator.addMethod("textValid", function (value, element) {
            var p =/<%=DictItem.TEXT_PATTERN%>/;
            return p.test(value);
        }, "<%=DictItem.TEXT_PATTERN_MESSAGE%>");

        var dictItemParams = {
            url: '/dictItem/treegrid',
            toolbar: '#dictItemToolbar',
            pagination: false,
            treeView: true,
            treeId: "id",
            treeField: "text",
            treeRootLevel: 1,
            queryParams: function (params) {
                var temp = {
                    sort: params.sort,  //排序列名
                    sortOrder: params.order//排位命令（desc，asc）
                };
                return $.extend(temp, $('#dictItemForm').serializeObject());
            },
            columns: [
                {
                    title: '名称', field: 'text', align: 'left', width: '100', formatter: function (index, row) {
                    var opts = row['text'] + "<div style='float:right;margin-right: 5px;'>";
                    <shiro:hasPermission name="auth:dictItem:add">
                    opts += " <a href='javascript:void(0);' class='glyphicon glyphicon-plus' onclick=\"addDictItem(\'" + row.id + "\')\"></a> ";
                    </shiro:hasPermission>
                    <shiro:hasPermission name="auth:dictItem:edit">
                    opts += " <a href='javascript:void(0);' class='glyphicon glyphicon-pencil' onclick=\"editDictItem(\'" + row.id + "\')\"></a> ";
                    </shiro:hasPermission>
                    return opts
                }
                },
                {title: '值', field: 'value', align: 'center', width: '100'},
                {title: '排序', field: 'rank', align: 'center', width: '100'},
                {
                    title: '状态', field: 'status', align: 'center', width: '100', formatter: function (index, row) {
                    return anchor.getDictItemTextByValue(statusDict.list, row.status);
                }

                }
            ]
        };

        dictItemBt = anchor.bootstrapTable("dictItemTable", dictItemParams);


        $('#addDictItem').click(function () {
            addDictItem();
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
                anchor.request("/dictItem/deleteBatch", {ids: ids}, function (data) {
                    bt.bootstrapTable('refresh');
                }, null);
            });

        });

    });

    /**
     * 添加字典元素
     */
    function addDictItem(id) {
        var dictItem = $('#dictItemForm input[name=dictId]').val();
        if (!dictItem) {
            anchor.alert("请选择需要添加的字典");
            return;
        }
        var addFormId = "addDictItemForm";
        var addDialog = $.dialog({
            title: '',
            content: 'url:/dictItem/add',
            columnClass: 'medium',
            onContentReady: function () {
                $("#" + addFormId + " input[name=dictId]").val(dictItem);
                $("#" + addFormId + " input[name=pid]").val(id);
                var validateConfig = anchor.validFieldConfig(dictItemValidConfig, anchor.formField(addFormId));
                validateConfig['id'] = addFormId;
                var valid = anchor.validate(validateConfig);
                $('#saveDictItem').click(function () {
                    if (valid.form()) {
                        anchor.request("/dictItem/add", $('#' + addFormId).serializeObject(), function (data) {
                            if (data.code == 1) {
                                dictItemLoad();
                                anchor.alert("保存成功");
                                addDialog.close();
                            }
                            else {
                                anchor.alert(data.message);
                            }
                        }, null);
                    }
                });
            }

        });
    }

    function dictItemLoad() {
        var dictItem = $('#dictItemForm input[name=dictId]').val();
        if (dictItem) {
            dictItemBt.bootstrapTable('refresh');
        }
        else {
            anchor.alert("请选择需要查询的字典");
        }
    }

    /**
     * 编辑
     */
    function editDictItem(dictItemId) {
        var editFormId = "editDictItemForm";
        var dialog = $.dialog({
            title: '',
            content: 'url:/dictItem/edit/' + dictItemId,
            columnClass: 'medium',
            onContentReady: function () {
                var validateConfig = anchor.validFieldConfig(dictItemValidConfig, anchor.formField(editFormId));
                validateConfig['id'] = editFormId;
                var valid = anchor.validate(validateConfig);
                $('#editDictItem').click(function () {
                    if (valid.form()) {
                        anchor.request("/dictItem/edit", $('#' + editFormId).serializeObject(), function (data) {
                            dictItemLoad();
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