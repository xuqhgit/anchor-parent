<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">编辑【${ dict.name }】</h3>
        </div>
        <form id="editDictForm" class="form-horizontal" role="form">
            <input type="hidden" name="id" value="${ dict.id }">
            <div class="box-body">
                <div class="form-group">
                    <label class="col-sm-3 control-label">名称：</label>
                    <div class="col-sm-8">
                        <input type="text" name="name" value="${ dict.name }" class="form-control "
                               placeholder="长度不超过32个字符">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">编码：</label>
                    <div class="col-sm-8">
                        <input type="text" name="code" value="${ dict.code }" class="form-control "
                               placeholder="只能包含数字、字母、下划线、减号长度不能超过32个字符">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">状态：</label>
                    <div class="col-sm-8">
                        <input type="text" name="status" value="${ dict.status }" class="form-control " placeholder="">
                    </div>
                </div>
            </div>
            <div class="box-footer">
                <a id="editDict" class="btn btn-info pull-right">保存</a>
            </div>
        </form>
    </div>
</div>

