<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">新增字典元素</h3>
        </div>
        <form id="addDictItemForm" class="form-horizontal" role="form">
            <input type="hidden" name="dictId">
            <input type="hidden" name="pid">
            <div class="box-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">名称：</label>
                    <div class="col-sm-9">
                        <input type="text" name="text" class="form-control " placeholder="长度不超过32个字符">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">值：</label>
                    <div class="col-sm-9">
                        <input type="text" name="value" class="form-control " placeholder="只能包含数字、字母、下划线、减号长度不能超过32个字符">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">状态：</label>
                    <div class="col-sm-9">
                        <input type="text" name="status" class="form-control " placeholder="">
                    </div>
                </div>
            </div>
            <div class="box-footer">
                <a id="saveDictItem" class="btn btn-info pull-right">保存</a>
            </div>
        </form>
    </div>



</div>

