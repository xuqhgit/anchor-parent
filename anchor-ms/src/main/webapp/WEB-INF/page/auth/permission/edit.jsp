<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">编辑</h3>
        </div>
        <form id="editPermissionForm" class="form-horizontal" role="form">
            <input type="hidden" name="id" value="${ permission.id }">
            <div class="box-body">
                <div class="form-group">
                    <label class="col-sm-3 control-label">权限编码：</label>
                    <div class="col-sm-9">
                        <input type="text" name="code" class="form-control " value="${ permission.code }"
                               placeholder="请输入格式为:字母、数字、下划线、减号、冒号，1至32位字符">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">名称：</label>
                    <div class="col-sm-9">
                        <input type="text" name="name" class="form-control " value="${ permission.name }"
                               placeholder="长度1至16位字符">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">类型：</label>
                    <div class="col-sm-9">
                        <input type="text" name="type" class="form-control " value="${ permission.type }"
                               placeholder="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">URL：</label>
                    <div class="col-sm-9">
                        <input type="text" name="url" class="form-control " value="${ permission.url }"
                               placeholder="格式为a-zA-Z0-9_-/长度不能超过128个字符">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">排名：</label>
                    <div class="col-sm-9">
                        <input type="text" name="rank" class="form-control " value="${ permission.rank }"
                               placeholder="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">Target：</label>
                    <div class="col-sm-9">
                        <select name="targetType" class="form-control" data-value="${ permission.targetType }"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">图标：</label>
                    <div class="col-sm-9">
                        <input type="text" name="icon" class="form-control " value="${ permission.icon }"
                               placeholder="">
                    </div>
                </div>
            </div>
            <div class="box-footer">
                <a id="editPermission" class="btn btn-info pull-right">保存</a>
            </div>
        </form>
    </div>
</div>

