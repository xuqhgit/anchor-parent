<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">您正在为【${user.username}】用户分配角色</h3>
        </div>
        <div class="box-body">
            <form id="setRoleForm" class="form-horizontal" role="form">
                <input type="hidden" name="userId" value="${user.id}">
                <div class="form-group">
                    <div class="col-sm-10">

                    </div>

                </div>
            </form>
        </div>
        <div class="box-footer">
            <%--<button type="submit" class="btn btn-default">Cancel</button>--%>
            <a id="setRoleUser" class="btn btn-info pull-right">保存</a>
        </div>
    </div>

</div>


<script>
    $(document).ready(function () {
        var roleId<c:if test="${user.role!=null}"> = "${user.role.id}"</c:if>;
        anchor.request("/role/list", null, function (data) {
            var a = $('#setRoleForm .form-group div');
            data.data.forEach(function (val) {
                a.append("<label><input type='radio'  name='roleId' value='" + val.id + "'  " + (val.status != '1' ? 'disabled' : '') + " " + (roleId && roleId == val.id ? 'checked' : '') + ">" +
                        (val.status != '1' ? '<s>' + val.name + '</s>' : val.name) + "</label>");
            });
            $('#setRoleForm .form-group input').iCheck({
                checkboxClass: 'icheckbox_flat-blue',
                radioClass: 'iradio_flat-blue'
            });
        }, {filter: true})
    });


</script>


