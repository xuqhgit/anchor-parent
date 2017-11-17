<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">编辑【${dictItem.text}】</h3>
        </div>
        <form id="editDictItemForm" class="form-horizontal" role="form">
            <input type="hidden" name="id" value="${ dictItem.id }">
            <div class="box-body">

                <div class="form-group">
                    <label class="col-sm-2 control-label">名称：</label>
                    <div class="col-sm-8">
                         <input type="text" name="text" value="${ dictItem.text }" class="form-control " placeholder="长度不超过32个字符">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">值：</label>
                    <div class="col-sm-8">
                        <input type="text" name="value" value="${ dictItem.value }" class="form-control " placeholder="只能包含数字、字母、下划线、减号长度不能超过32个字符">
                    </div>
                </div>
            </div>
            <div class="box-footer">
                <a id="editDictItem" class="btn btn-info pull-right">保存</a>
            </div>
        </form>
    </div>
</div>

