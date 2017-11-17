
<%@ page contentType="text/html;charset=UTF-8" import="com.anchor.ms.auth.model.DictItem" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>字典元素管理</title>
    <%@include file="../../common.jsp" %>
</head>
<body>


<div class="container-fluid ">
    <h3>字典元素列表</h3>

    <div class="row-fluid ">
        <div class="span12 search-form">
            <form id="form" class="form-horizontal" role="form">
                <div class="form-group">
                      
                    <label class="col-sm-1 control-label" for="text">名称：</label>
                    <div class="col-sm-2">
                        <input class="form-control" id="text" name="text" type="text" placeholder="名称"/>
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
                    <a role="button" id="addDictItem" class="btn btn-default">
                        <i class="glyphicon glyphicon-plus"></i>添加字典元素
                    </a>
                </shiro:hasPermission>

                <shiro:hasPermission name=":deleteBatch">
                    <a href="#addDictItemModal" role="button" class="btn btn-default" id="deleteBatchButton">
                        <i class="glyphicon glyphicon-trash"></i>批量删除
                    </a>
                </shiro:hasPermission>
            </div>
            <table id="dictItemTable"></table>
        </div>
    </div>
</div>





<%@include file="../../common_script.jsp" %>
<script>

    var bt;
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
        },
    };
    $(function () {
    
    jQuery.validator.addMethod("keyValid", function(value,element) {
        var p =/<%=DictItem.VALUE_PATTERN%>/;
        return p.test(value);
    }, "<%=DictItem.VALUE_PATTERN_MESSAGE%>");
    jQuery.validator.addMethod("textValid", function(value,element) {
        var p =/<%=DictItem.TEXT_PATTERN%>/;
        return p.test(value);
    }, "<%=DictItem.TEXT_PATTERN_MESSAGE%>");

    var params = {
            url: '/dictItem/grid',
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
                {title: '名称', field: 'text', align: 'center', width: '100'},
                {title: '值', field: 'value', align: 'center', width: '100'},
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
        bt = anchor.bootstrapTable("dictItemTable", params);
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
                anchor.request("/dictItem/deleteBatch", {ids: ids}, function (data) {
                    bt.bootstrapTable('refresh');
                }, null);
            });

        });

    });
    /**
     * 详情
     */
    function detailDictItem(dictItemId) {
        $.dialog({
            title: '',
            content: 'url:/dictItem/edit/'+dictItemId,
            type:'blue',
            columnClass:'medium',
            onContentReady:function(){

            }
        });
    }
    /**
     * 删除
     */
    function deleteDictItem(dictItemId) {
        anchor.confirm("确认要删除该用户么?", function () {
            anchor.request("/dictItem/delete/"+dictItemId, {}, function (data) {
                anchor.alert(data.message);
                bt.bootstrapTable('refresh');
            }, null);
        });
    }

</script>
</body>
</html>