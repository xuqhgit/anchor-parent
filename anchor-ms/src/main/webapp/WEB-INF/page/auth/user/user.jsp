
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户管理</title>
    <%@include file="../../common.jsp"%>
</head>
<body>


<div class="content body">
    <table id="userTable"></table>
    <div id="jqGridPager"></div>
</div>

<%@include file="../../common_script.jsp"%>
<script>
    var params={
        url: '/user/grid',
        height:500,
        colModel: [
            { label: 'id', name: 'id', key: true, width: 75 },
            { label: '用户名', name: 'username', width: 150 },
            { label: '名称', name: 'name', width: 150 },
            { label: '状态', name: 'state', width: 150 },
            { label:'操作', name: 'opt', width: 150 }
        ],
    };
    createJqGrid("userTable",params);
</script>
</body>
</html>
