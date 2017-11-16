<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="form-dialog">
    <div class="box box-info">
        <div class="box-header ">
            <h3 class="box-title">新增字典</h3>
        </div>
        <form id="addDictForm" class="form-horizontal" role="form">
            <div class="box-body">
                
                <div class="form-group">
                    <label class="col-sm-3 control-label">字典编码：</label>
                    <div class="col-sm-9">
                        <input type="text" name="code" class="form-control " placeholder="只能包含数字、字母、下划线、减号长度不能超过32个字符">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">名称：</label>
                    <div class="col-sm-9">
                        <input type="text" name="name" class="form-control " placeholder="长度不超过32个字符">
                    </div>
                </div>
            </div>
            <div class="box-footer">
                <a id="saveDict" class="btn btn-info pull-right">保存</a>
            </div>
        </form>
    </div>

    <script type="text/javascript">
        $("#addDictForm .form_datetime").datetimepicker({format: 'yyyy-mm-dd'});
    </script>

</div>

