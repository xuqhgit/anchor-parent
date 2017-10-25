<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">编辑</h3>
        </div>
        <form id="editRoleForm" class="form-horizontal" role="form">
            <input type="hidden" name="id" value="${ role.id}">
            <div class="box-body">
                
                <div class="form-group">
                    <label class="col-sm-2 control-label">角色编码：</label>
                    <div class="col-sm-10">
                        <input type="text" name="code" class="form-control" value="${ role.code }" placeholder="请输入英文大小写、数字、下划线、减号1到16位字符">
                        <span class="help-block m-b-none" ></span>

                    </div>
                </div>
                 
                 
                 
                 
                <div class="form-group">
                    <label class="col-sm-2 control-label">名称：</label>
                    <div class="col-sm-10">
                        <input type="text" name="name" class="form-control" value="${ role.name }" placeholder="请输入1到16位字符">
                        <span class="help-block m-b-none" ></span>

                    </div>
                </div>
                 
                 
                 
            </div>
            <div class="box-footer">
                <a id="editRole" class="btn btn-info pull-right">保存</a>
            </div>
        </form>
    </div>
</div>

