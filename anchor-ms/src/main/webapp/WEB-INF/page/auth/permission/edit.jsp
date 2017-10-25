<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">编辑</h3>
        </div>
        <form id="editPermissionForm" class="form-horizontal" role="form">
            <input type="hidden" name="id" value="${ permission.id}">
            <div class="box-body">
                
                 
                 
                 
                <div class="form-group">
                    <label class="col-sm-2 control-label">：</label>
                    <div class="col-sm-10">
                        <input type="text" name="icon" class="form-control" value="${ permission.icon }" placeholder="">
                        <span class="help-block m-b-none" ></span>

                    </div>
                </div>
                 
                 
                <div class="form-group">
                    <label class="col-sm-2 control-label">：</label>
                    <div class="col-sm-10">
                        <input type="text" name="name" class="form-control" value="${ permission.name }" placeholder="">
                        <span class="help-block m-b-none" ></span>

                    </div>
                </div>
                 
                 
                <div class="form-group">
                    <label class="col-sm-2 control-label">：</label>
                    <div class="col-sm-10">
                        <input type="text" name="rank" class="form-control" value="${ permission.rank }" placeholder="">
                        <span class="help-block m-b-none" ></span>

                    </div>
                </div>
                 
                 
                <div class="form-group">
                    <label class="col-sm-2 control-label">：</label>
                    <div class="col-sm-10">
                        <input type="text" name="type" class="form-control" value="${ permission.type }" placeholder="">
                        <span class="help-block m-b-none" ></span>

                    </div>
                </div>
                 
                 
                 
            </div>
            <div class="box-footer">
                <a id="editPermission" class="btn btn-info pull-right">保存</a>
            </div>
        </form>
    </div>
</div>

