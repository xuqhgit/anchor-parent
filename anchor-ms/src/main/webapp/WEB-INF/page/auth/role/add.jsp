<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">新增角色</h3>
        </div>
        <form id="addRoleForm" class="form-horizontal" role="form">

            <div class="box-body">
                
                <div class="form-group">
                    <label class="col-sm-3  control-label">角色编码：</label>
                    <div class="col-sm-9 ">
                        <input type="text" name="code" class="form-control" placeholder="请输入英文大小写、数字、下划线、减号1到16位字符">
                    </div>
                </div>
                
                
                
                
                
                <div class="form-group">
                    <label class="col-sm-3 control-label">名称：</label>
                    <div class="col-sm-9">
                        <input type="text" name="name" class="form-control" placeholder="请输入1到16位字符">
                    </div>
                </div>
                
                
                
                
            </div>
            <div class="box-footer">
                <%--<button type="submit" class="btn btn-default">Cancel</button>--%>
                <a id="saveRole" class="btn btn-info pull-right">保存</a>
            </div>
        </form>
    </div>
</div>

