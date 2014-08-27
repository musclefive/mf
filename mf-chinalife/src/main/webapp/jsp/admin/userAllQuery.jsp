<%--
  Created by IntelliJ IDEA.
  User: Chao.Cui.VWED
  Date: 14-5-15
  Time: 下午12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:import url="/template/importedFiles.html" />

    <link href="/mf-chinalife/css/demo_table.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" language="javascript" src="../../js/jquery.dataTables1.10.js"></script>

    <script type="application/javascript" language="JavaScript">
        $(document).ready(function() {
            var table = $('#userList').DataTable({
                "bProcessing": true,
                "ajax": {
                    "url" : "/mf-chinalife/queryUsers",
                    "dataSrc" : "items"
                },
                "columns": [
                    { "data": "id" },
                    { "data": "nickname" },
                    { "data": "email" },
                    { "data": "password" },
                    { "data": "user_category" },
                    { "data": "createTime" },
                    {
                        "targets": -1,
                        "data": null,
//                        "defaultContent": "<button>Click!</button>"
                        "defaultContent": "<input type='checkbox' name='topOnMainPage'  checked/>&nbsp;" +
                                "<button class='btn btn-primary btn-xs' title='View' type='button'>" +
                                "<span class='glyphicon glyphicon-eye-open'></span></button>&nbsp;" +
                                "<button class='btn btn-danger btn-xs' title='Delete' type='button'>" +
                                "<span class='glyphicon glyphicon-trash'></span></button>"
                    }
                ],
                stateSave: true,
                "drawCallback": function( settings ) {
                    $("button[title='Delete']").on('click', function(){
                        var data = table.row( $(this).parents('tr') ).data();
//                        if(confirm("确定要删除")){
//                            table.row( $(this).parents('tr') ).remove().draw( false );
//                        }
                        $.ajax({
                            url: "/mf-chinalife/queryUser?type=delete&user_id="+data["id"],
                            type:"get",
//                            dateType : "json",
                            success:function(msg){
                                table.row( $(this).parents('tr') ).remove().draw( false );
                                alert(msg);
//                                alert("删除成功");
                            },
                            error : function(textStatus){
                                alert("error:" + textStatus.error);
                            }
                        });

                    } );
                }
            })
        })
    </script>
</head>
<body>
<c:import url="/template/header.jsp" />
<div class="container">
    <div class="row clearfix">
        <div class="col-md-2">
            <%@ include file="/template/left.jsp"%>
        </div>
        <div class="col-md-10">
            <h3>用户列表</h3>
            <table class="table table-hover" id="userList">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>用户名</th>
                    <th>邮箱</th>
                    <th>密码</th>
                    <th>角色</th>
                    <th>注册时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
