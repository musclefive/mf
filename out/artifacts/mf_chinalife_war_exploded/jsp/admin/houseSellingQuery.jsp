<%--
  Created by IntelliJ IDEA.
  User: Chao.Cui.VWED
  Date: 14-3-23
  Time: 下午4:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:import url="/template/importedFiles.html"/>
    <link href="/mf-chinalife/css/demo_table.css" rel="stylesheet" type="text/css"/>
    <link href="../../css/bootstrap-switch.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript" language="javascript" src="../../js/jquery.dataTables1.10.js"></script>
    <script src="../../js/bootstrap-switch.js" type="text/javascript"></script>
    <script type="application/javascript" language="JavaScript">
        $(document).ready(function () {
            $(document).ready(function() {
                var table = $('#houseList').DataTable({
                    "bProcessing": true,
                    "ajax": {
                        "url" : "/mf-chinalife/queryHouses",
                        "dataSrc" : "items"
                    },
                    stateSave: true,
                    "columns": [
                        { "data": "houseId" },
                        { "data": "title" },
                        { "data": "district" },
                        { "data": "status" },
                        { "data": "address" },
                        {
                            "targets": 0,
                            "data": null,
//                        "defaultContent": "<button>Click!</button>"
                            "defaultContent": "<input type='checkbox' name='topOnAdvertise'  checked/>"
                        },
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
                    "drawCallback": function( settings ) {
                        // Output the data for the visible rows to the browser's console
                        $("input[name='topOnAdvertise']").bootstrapSwitch({
                            onColor : "success",
                            offColor : "danger",
                            size : "mini",
                            onSwitchChange : function(event, state) {
                                alert(state);
                                var data = table.row( $(this).parents('tr') ).data();
                                alert( "view  " + data["houseId"] +"'s salary is: "+ data[ 3 ] );
                            }
                        });

                        $("input[name='topOnMainPage']").bootstrapSwitch({
                            onColor : "success",
                            offColor : "danger",
                            size : "mini",
                            onText : "是",
                            offText : "否",
                            onSwitchChange : function(event, state) {
                                alert(state);
                                var data = table.row( $(this).parents('tr') ).data();
                                alert( "view  " + data["houseId"] +"'s salary is: "+ data[ 3 ] );
                            }
                        });

                        $("button[title='View']").on('click', function(){
                            var data = table.row( $(this).parents('tr') ).data();
//                        alert( "view  " + data["houseId"] +"'s salary is: "+ data[ 3 ] );
                            location.href = "/mf-chinalife/querySingleHouse?house_id="+data["houseId"];
                        })
                        $("button[title='Delete']").on('click', function(){
                            var data = table.row( $(this).parents('tr') ).data();
                            alert("delete:" +  data["houseId"] +"'s salary is: "+ data["address"] );
                            var data = table.row( $(this).parents('tr') ).data();
                            if(confirm("确定要删除")){
                                table.row( $(this).parents('tr') ).remove().draw( false );
                            }
                        } );
                    }
                });

            } );
        });
    </script>
</head>
<body>
<!--navigation for left area-->
<%@ include file="/template/header.jsp" %>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-2">
            <%@ include file="/template/left.jsp" %>
        </div>
        <div class="col-md-10">
            <table class="table table-hover" id="houseList">
                <thead>
                <tr>
                    <th style="width: 10%">序号</th>
                    <th style="width: 20%">标题</th>
                    <th style="width: 10%">小区</th>
                    <th style="width: 10%">价格</th>
                    <th style="width: 20%">发布时间</th>
                    <th style="width: 10%">首页广告</th>
                    <th style="width: 20%">推荐位置</th>
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
