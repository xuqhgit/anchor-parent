<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">新增用户</h3>
        </div>
        <form id="addUserForm" class="form-horizontal" role="form">

            <div class="box-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户名：</label>
                    <div class="col-sm-10">
                        <input type="text" name="username" class="form-control" placeholder="输入用户名6~20位">

                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">名称：</label>
                    <div class="col-sm-10">
                        <input type="text" name="name" class="form-control" placeholder="2~10个字符">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Email：</label>
                    <div class="col-sm-10">
                        <input type="text" name="email" class="form-control" placeholder="email">
                    </div>
                </div>
            </div>
            <div class="box-footer">
                <%--<button type="submit" class="btn btn-default">Cancel</button>--%>
                <a id="saveUser" class="btn btn-info pull-right">保存</a>
            </div>
        </form>
    </div>
</div>


