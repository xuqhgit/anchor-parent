<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">编辑</h3>
        </div>
        <form id="editDictItemForm" class="form-horizontal" role="form">
            <input type="hidden" name="id" value="${ dictItem.id }">
            <div class="box-body">
                <div class="form-group">
                    <label class="col-sm-3 control-label">名称：</label>
                    <div class="col-sm-9">
                         <input type="text" name="text" class="form-control " placeholder="长度不超过32个字符">
                    </div>
                </div>
            </div>
            <div class="box-footer">
                <a id="editDictItem" class="btn btn-info pull-right">保存</a>
            </div>
        </form>
    </div>
</div>

