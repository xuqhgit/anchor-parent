<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">新增权限</h3>
        </div>
        <form id="addPermissionForm" class="form-horizontal" role="form">

            <div class="box-body">
                
                <div class="form-group">
                    <label class="col-sm-2 control-label">：</label>
                    <div class="col-sm-10">
                        <input type="text" name="code" class="form-control" placeholder="请输入英文大小写、数字、下划线、减号、冒号1到32位字符">
                    </div>
                </div>
                
                
                
                
                <div class="form-group">
                    <label class="col-sm-2 control-label">：</label>
                    <div class="col-sm-10">
                        <input type="text" name="icon" class="form-control" placeholder="">
                    </div>
                </div>
                
                
                
                <div class="form-group">
                    <label class="col-sm-2 control-label">：</label>
                    <div class="col-sm-10">
                        <input type="text" name="name" class="form-control" placeholder="">
                    </div>
                </div>
                
                
                
                <div class="form-group">
                    <label class="col-sm-2 control-label">：</label>
                    <div class="col-sm-10">
                        <input type="text" name="rank" class="form-control" placeholder="">
                    </div>
                </div>
                
                
                <div class="form-group">
                    <label class="col-sm-2 control-label">：</label>
                    <div class="col-sm-10">
                        <input type="text" name="state" class="form-control" placeholder="">
                    </div>
                </div>
                
                
                <div class="form-group">
                    <label class="col-sm-2 control-label">：</label>
                    <div class="col-sm-10">
                        <input type="text" name="type" class="form-control" placeholder="">
                    </div>
                </div>
                
                
                
                <div class="form-group">
                    <label class="col-sm-2 control-label">：</label>
                    <div class="col-sm-10">
                        <input type="text" name="url" class="form-control" placeholder="">
                    </div>
                </div>
                
                
            </div>
            <div class="box-footer">
                <%--<button type="submit" class="btn btn-default">Cancel</button>--%>
                <a id="savePermission" class="btn btn-info pull-right">保存</a>
            </div>
        </form>
    </div>
</div>

