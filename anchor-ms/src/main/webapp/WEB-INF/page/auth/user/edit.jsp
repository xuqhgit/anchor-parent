<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">编辑【${user.username}】用户</h3>
        </div>
        <form id="editUserForm" class="form-horizontal" role="form">
            <input type="hidden" name="id" value="${user.id}">
            <div class="box-body">

                <div class="form-group">
                    <label class="col-sm-2 control-label">名称：</label>
                    <div class="col-sm-10">
                        <input type="text" name="name" class="form-control" value="${user.name}" placeholder="名称:1~10个字符">
                        <span class="help-block m-b-none" ></span>

                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Email：</label>
                    <div class="col-sm-10">
                        <input type="text" name="email" value="${user.email}" class="form-control" placeholder="email">
                    </div>
                </div>
            </div>
            <div class="box-footer">
                <%--<button type="submit" class="btn btn-default">Cancel</button>--%>
                <a id="editUser" class="btn btn-info pull-right">保存</a>
            </div>
        </form>
    </div>
</div>


