
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
                <form id="form" class="form-horizontal" role="form">
                    <div class="form-group">

                        <label class="col-sm-2 control-label" for="code">字典编码：</label>
                        <div class="col-sm-4">
                            <input class="form-control" id="code" name="code" type="text" placeholder="字典编码"/>
                        </div>

                        <label class="col-sm-2 control-label" for="name">名称：</label>
                        <div class="col-sm-4">
                            <input class="form-control" id="name" name="name" type="text" placeholder="名称"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="status">状态：</label>
                        <div class="col-sm-4">
                            <input class="form-control" id="status" name="status" type="text" placeholder="状态"/>
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
                    <shiro:hasPermission name=":add:index">
                        <a role="button" id="addDict" class="btn btn-default">
                            <i class="glyphicon glyphicon-plus"></i>添加字典
                        </a>
                    </shiro:hasPermission>

                    <shiro:hasPermission name=":deleteBatch">
                        <a href="#addDictModal" role="button" class="btn btn-default" id="deleteBatchButton">
                            <i class="glyphicon glyphicon-trash"></i>批量删除
                        </a>
                    </shiro:hasPermission>
                </div>
                <table id="dictTable"></table>
            </div>
        </div>
        <div class="col-md-7">
            <div class="span12 search-form">
                <form id="dictItemForm" class="form-horizontal" role="form">
                    <input type="hidden" name="dictId" value="1">
                </form>
            </div>
            <div class="span12">
                <div id="dictItemToolbar" class="btn-group">
                    <shiro:hasPermission name="auth:dict:add:index">
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
    var dictValidConfig={
    
        code:{
            rule:{
                required:true,
                codeValid:true 
            },
            message:{
                required:'必填项'
            }
        },
        name:{
            rule:{
                required:true,
                nameValid:true 
            },
            message:{
                required:'必填项'
            }
        },
    };
    $(function () {
    
    jQuery.validator.addMethod("codeValid", function(value,element) {
        var p =/<%=Dict.CODE_PATTERN%>/;
        return p.test(value);
    }, "<%=Dict.CODE_PATTERN_MESSAGE%>");
    jQuery.validator.addMethod("nameValid", function(value,element) {
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
                {checkbox: true},
                 
                {title: '字典编码', field: 'code', align: 'center',sortable: true, width: '100'},
                
                {title: '创建时间', field: 'createTime', align: 'center', width: '100'},
                
                
                
                {title: '名称', field: 'name', align: 'center', width: '100'},
                
                {title: '状态', field: 'status', align: 'center', width: '100'},
                
                
                {
                    title: '操作', field: 'opt', align: 'center', width: '120', formatter: function (index, row) {
                    var opts = "";
                    <shiro:hasPermission name=":get">
                        opts += "<a href='javascript:void(0);' class='btn btn-xs' onclick=\"detailDict(\'" + row.id + "\')\">查看</a>|";
                    </shiro:hasPermission>
                    <shiro:hasPermission name=":edit:index">
                        opts += "<a href='javascript:void(0);' class='btn btn-xs' onclick=\"editDict(\'" + row.id + "\')\">编辑</a>|";
                    </shiro:hasPermission>
                    <shiro:hasPermission name=":delete">
                        opts += "<a href='javascript:void(0);' class='btn btn-xs' onclick=\"deleteDict(\'" + row.id + "\')\">删除</a>";
                    </shiro:hasPermission>
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
                columnClass:'medium',
                onContentReady:function(){
                    var validateConfig =anchor.validFieldConfig(dictValidConfig,anchor.formField(addFormId));
                    validateConfig['id']= addFormId;
                    var valid = anchor.validate(validateConfig);
                    $('#saveDict').click(function () {
                        if(valid.form()){
                            <shiro:hasPermission name="auth:dict:add">
                                anchor.request("/dict/add", $('#'+addFormId).serializeObject(), function (data) {
                                if(data.code==1){
                                    bt.bootstrapTable('refresh');
                                    anchor.alert("保存成功");
                                    addDialog.close();
                                }
                                else{
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
    function detailDict(dictId) {
        $.dialog({
            title: '',
            content: 'url:/dict/edit/'+dictId,
            type:'blue',
            columnClass:'medium',
            onContentReady:function(){

            }
        });
    }
    /**
     * 删除
     */
    function deleteDict(dictId) {
        anchor.confirm("确认要删除该用户么?", function () {
            anchor.request("/dict/delete/"+dictId, {}, function (data) {
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
            content: 'url:/dict/edit/'+dictId,
            columnClass:'medium',
            onContentReady:function(){
                var validateConfig =anchor.validFieldConfig(dictValidConfig,anchor.formField(editFormId));
                validateConfig['id']= editFormId;
                var valid = anchor.validate(validateConfig);
                $('#editDict').click(function () {
                    if(valid.form()){
                        anchor.request("/dict/edit", $('#'+editFormId).serializeObject(), function (data) {
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
    var dictItemValidConfig={
        key:{
            rule:{
                required:true,
                keyValid:true
            },
            message:{
                required:'必填项'
            }
        },
        text:{
            rule:{
                required:false,
                textValid:true
            },
            message:{
                required:'必填项'
            }
        }
    };
    $(function () {

        jQuery.validator.addMethod("keyValid", function(value,element) {
            var p =/<%=DictItem.KEY_PATTERN%>/;
            return p.test(value);
        }, "<%=DictItem.KEY_PATTERN_MESSAGE%>");
        jQuery.validator.addMethod("textValid", function(value,element) {
            var p =/<%=DictItem.TEXT_PATTERN%>/;
            return p.test(value);
        }, "<%=DictItem.TEXT_PATTERN_MESSAGE%>");

        var params = {
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
                {title: '名称', field: 'text', align: 'left', width: '100'},
                {title: '编码', field: 'code', align: 'center', width: '100'},
                {title: '状态', field: 'status', align: 'center', width: '100'},
                {
                    title: '操作', field: 'opt', align: 'center', width: '120', formatter: function (index, row) {
                    var opts = "";
                    <shiro:hasPermission name=":get">
                    opts += "<a href='javascript:void(0);' class='btn btn-xs' onclick=\"detailDictItem(\'" + row.id + "\')\">查看</a>|";
                    </shiro:hasPermission>
                    <shiro:hasPermission name=":edit:index">
                    opts += "<a href='javascript:void(0);' class='btn btn-xs' onclick=\"editDictItem(\'" + row.id + "\')\">编辑</a>|";
                    </shiro:hasPermission>
                    <shiro:hasPermission name=":delete">
                    opts += "<a href='javascript:void(0);' class='btn btn-xs' onclick=\"deleteDictItem(\'" + row.id + "\')\">删除</a>";
                    </shiro:hasPermission>
                    return opts;
                }
                }
            ]
        };
        dictItemBt = anchor.bootstrapTable("dictItemTable", params);
        $('#dictItemSearch').click(function () {
            dictItemBt.bootstrapTable('refresh');
        });
        //回车事件绑定
        document.onkeydown = function (event) {
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if (e && e.keyCode == 13) {
                $('#dictItemSearch').click();
            }
        };

        $('#addDictItem').click(function () {
            var addFormId = "addDictItemForm";
            var addDialog = $.dialog({
                title: '',
                content: 'url:/dictItem/add',
                columnClass:'medium',
                onContentReady:function(){
                    var validateConfig =anchor.validFieldConfig(dictItemValidConfig,anchor.formField(addFormId));
                    validateConfig['id']= addFormId;
                    var valid = anchor.validate(validateConfig);
                    $('#saveDictItem').click(function () {
                        if(valid.form()){
                            <shiro:hasPermission name="auth:dictItem:add">
                            anchor.request("/dictItem/add", $('#'+addFormId).serializeObject(), function (data) {
                                if(data.code==1){
                                    dictItemBt.bootstrapTable('refresh');
                                    anchor.alert("保存成功");
                                    addDialog.close();
                                }
                                else{
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
                anchor.request("/dictItem/deleteBatch", {ids: ids}, function (data) {
                    bt.bootstrapTable('refresh');
                }, null);
            });

        });

    });
</script>
</body>
</html>