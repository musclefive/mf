<%--
  Created by IntelliJ IDEA.
  User: Chao.Cui.VWED
  Date: 14-3-18
  Time: 上午11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:import url="/template/importedFiles.html" />
    <link href="../../css/demo_table.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" language="javascript" src="../../js/jquery.dataTables.js"></script>
    <script src="../../js/jquery.tmpl.js"></script>

    <script type="text/javascript" charset="utf-8">
        $(document).ready(function() {
            /*$('#houseList').dataTable( {
                "sPaginationType": "full_numbers",
                "bLengthChange": false, //改变每页显示数据数量
                "bFilter": false,
                "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
                    *//* Append the grade to the default row class name *//*
                    if ( aData[4] == "A" )
                    {
                        $('td:eq(4)', nRow).html( '<b>A</b>' );
                    }
                }
            } );
            $('#houseList tbody tr').on('click', function () {
                var sTitle;
                var nTds = $('td', this);
                var sBrowser = $(nTds[1]).text();
                var sGrade = $(nTds[2]).text();
                alert( sBrowser + " :" + sGrade )
            } );*/
        } );
    </script>
    <style type="text/css">

    </style>
</head>
<body>
<!--navigation for top area-->
<%@ include file="/template/header.jsp"%>
<div class="container">
    <a href="#">Chinalife</a>-&gt;<a href="#"> 房产</a>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">
                区域地标
            </h3>
        </div>
        <div class="panel-body">
            <div class="row clearfix">
                <!--条件筛选区域-->
                <div class="col-md-9 column">
                    <dl class="dl-horizontal">
                        <dt>
                            <a href="#" class="btn btn-success">
                                <span class="glyphicon glyphicon-tree-conifer"></span>郊区选择</a>
                        </dt>
                        <dd style="padding-top: 3px;padding-bottom: 3px">
                            <a href="#" class="btn  btn-xs btn-default">市中心</a>
                            <a href="#" class="btn  btn-xs btn-default">市中心</a>
                            <a href="#" class="btn  btn-xs btn-default">市中心</a>
                        </dd>
                        <dt>
                            <a href="#" class="btn  btn-success">
                                <span class="glyphicon glyphicon-home"></span>区域选择</a>
                        </dt>
                        <dd  style="padding-top: 3px;padding-bottom: 3px">
                            <a href="#" class="btn  btn-xs btn-default">市中心</a>
                            <a href="#" class="btn  btn-xs btn-default">东区</a>
                            <a href="#" class="btn  btn-xs btn-default">南区</a>
                            <a href="#" class="btn  btn-xs btn-default">西区</a>
                            <a href="#" class="btn  btn-xs btn-default">北区</a>
                            <a href="#" class="btn  btn-xs btn-default">东北区</a>
                            <a href="#" class="btn  btn-xs btn-default">东南区</a>
                            <a href="#" class="btn  btn-xs btn-default">西北区</a>
                            <a href="#" class="btn  btn-xs btn-default">西南区</a>
                        </dd>
                        <dt>
                            <a href="#" class="btn btn-success">
                                <span class="glyphicon glyphicon-euro"></span>价格选择</a>
                        </dt>
                        <dd  style="padding-top: 3px;padding-bottom: 3px">
                            <a href="#" class="btn  btn-xs btn-default">0-10W</a>
                            <a href="#" class="btn  btn-xs btn-default">10-20W</a>
                            <a href="#" class="btn  btn-xs btn-default">20-30W</a>
                            <a href="#" class="btn  btn-xs btn-default">>30-50W</a>
                            <a href="#" class="btn  btn-xs btn-default">50-80W</a>
                            <a href="#" class="btn  btn-xs btn-default">80-100W</a>
                            <a href="#" class="btn  btn-xs btn-default">100W+</a>
                        </dd>
                        <dt>
                            <a href="#" class="btn btn-success">
                                <span class="glyphicon glyphicon-home"></span>房屋类型</a>
                        </dt>
                        <dd  style="padding-top: 3px;padding-bottom: 3px">
                            <a href="#" class="btn  btn-xs btn-default">房子</a>
                            <a href="#" class="btn  btn-xs btn-default">公寓</a>
                            <a href="#" class="btn  btn-xs btn-default">单位</a>
                            <a href="#" class="btn  btn-xs btn-default">联排别墅</a>
                            <a href="#" class="btn  btn-xs btn-default">土地</a>
                            <a href="#" class="btn  btn-xs btn-default">偏远地产</a>
                        </dd>
                        <dt>
                            <a href="#" class="btn btn-success">
                                <span class="glyphicon glyphicon-hand-right"></span>房间数量</a>
                        </dt>
                        <dd  style="padding-top: 3px;padding-bottom: 3px">
                            <a href="#" class="btn  btn-xs btn-default">1室</a>
                            <a href="#" class="btn  btn-xs btn-default">2室</a>
                            <a href="#" class="btn  btn-xs btn-default">3室</a>
                            <a href="#" class="btn  btn-xs btn-default">4室</a>
                            <a href="#" class="btn  btn-xs btn-default">5室</a>
                            <a href="#" class="btn  btn-xs btn-default">更多</a>
                        </dd>
                        <dt>
                            <a href="#" class="btn btn-success">
                                <span class="glyphicon glyphicon-hand-right"></span>车位数量</a>
                        </dt>
                        <dd  style="padding-top: 3px;padding-bottom: 3px">
                            <a href="#" class="btn  btn-xs btn-default">1个</a>
                            <a href="#" class="btn  btn-xs btn-default">2个</a>
                            <a href="#" class="btn  btn-xs btn-default">更多</a>
                        </dd>
                        <dt>
                            <a href="#" class="btn btn-success">
                                <span class="glyphicon glyphicon-hand-right"></span>卫浴数量</a>
                        </dt>
                        <dd  style="padding-top: 3px;padding-bottom: 3px">
                            <a href="#" class="btn  btn-xs btn-default">1个</a>
                            <a href="#" class="btn  btn-xs btn-default">2个</a>
                            <a href="#" class="btn  btn-xs btn-default">更多</a>
                        </dd>
                    </dl>

                </div>
                <div class="col-md-3 column">
                    <button type="button" class="btn btn-success btn-block">免费发布信息</button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-9">
            <div class="tabbable" id="tabs-204282">
                <ul class="nav nav-tabs">
                    <li>
                        <a href="#panel-361059" data-toggle="tab">中介</a>
                    </li>
                    <li class="active">
                        <a href="#panel-775800" data-toggle="tab">个人</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="panel-361059">
                        <table class="table table-hover" id="houseList">
                            <thead style="visibility: hidden">
                            <tr>
                                <th width="20%"></th>
                                <th width="80%"></th>
                            </tr>
                            </thead>
                            <tbody id="houseSellBody">
                            </tbody>
                        </table>
                        <!--pagination-->
                        <div class="col-md-12 column text-center">
                            <ul class="pagination pagination-lg">
                                <li>
                                    <a href="#">Prev</a>
                                </li>
                                <li>
                                    <a href="#">1</a>
                                </li>
                                <li>
                                    <a href="#">2</a>
                                </li>
                                <li>
                                    <a href="#">3</a>
                                </li>
                                <li>
                                    <a href="#">4</a>
                                </li>
                                <li>
                                    <a href="#">5</a>
                                </li>
                                <li>
                                    <a href="#">Next</a>
                                </li>
                            </ul>
                        </div>
                        <script id="houseSellTemplate" type="text/x-jquery-tmpl">
    <tr class="media">
    <td><img data-src="{{= Pic}}" class="img-rounded"></td>
    <td>
        <div class="row">
            <div class="col-md-12">
                <h4 class="media-heading">
                    <a href="#" target="_parent" class="text-info">{{= Price}}</a>
                    <a href="#" class="btn btn-default pull-right" title="Please log in to favorite this snippet">
                        <span class="glyphicon glyphicon-thumbs-up"></span> 10</a>
                </h4>
            </div>
        </div>
        <div class="row">
            <div class="col-md-8">
                <ul class="list-inline">
                    <li>{{= Area}} SqFt</li>
                    <li style="list-style: none">|</li>
                    <li>{{= Bed}} Beds</li>
                    <li style="list-style: none">|</li>
                    <li>{{= Baths}} Baths</li>
                    <li style="list-style: none">|</li>
                    <li>{{= Carport}} Carport</li>
                </ul>
                <ul class="list-inline">
                    <li>
                <span class="input-group-btn">
                    <button type="button" class="btn btn-primary btn-xs">
                        <span class="glyphicon glyphicon-hand-right"></span>
                    </button>
                    <text>{{= Status}}</text>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <text class="text-info">2014-05-05 11:11</text>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <text class="text-info">{{= District}}</text>
                </span>
                    </li>
                </ul>
                <p class="hidden-xs">
                    {{= Title}}
                </p>
            </div>
            <div class="col-md-4">
                <img data-src="holder.js/120x100" class="img-rounded img-responsive">
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <span class="input-group-btn text-muted">
                    <button type="button" class="btn btn-primary btn-xs">
                        <span class="glyphicon glyphicon-home"></span>
                    </button>
                    {{= Address}}
                </span>
            </div>
        </div>
    </td>
</tr>
</script>
                        <script type="text/javascript">
                            var houseData = [
                                { id: "1", Pic: "holder.js/200x185", Title: "万达广场真正的一室一厅，设施全，看好来电", Price: "500W", Area: "100", Bed: "1", Baths: "2", Carport: "2", District:"东南区", Status: "正在出售", Address: "捷达大路1777号", Description: "捷达大路1777号捷达大路1777号捷达大路1777号捷达大路1777号捷达大路1777号"},
                                { id: "1", Pic: "holder.js/200x185", Title: "万达广场真正的一室一厅，设施全，看好来电", Price: "500W", Area: "100", Bed: "1", Baths: "2", Carport: "2", District:"东南区", Status: "正在出售", Address: "捷达大路1777号", Description: "捷达大路1777号捷达大路1777号捷达大路1777号捷达大路1777号捷达大路1777号"},
                                { id: "1", Pic: "holder.js/200x185", Title: "万达广场真正的一室一厅，设施全，看好来电", Price: "500W", Area: "100", Bed: "1", Baths: "2", Carport: "2", District:"东南区", Status: "正在出售", Address: "捷达大路1777号", Description: "捷达大路1777号捷达大路1777号捷达大路1777号捷达大路1777号捷达大路1777号"},
                                { id: "1", Pic: "holder.js/200x185", Title: "万达广场真正的一室一厅，设施全，看好来电", Price: "500W", Area: "100", Bed: "1", Baths: "2", Carport: "2", District:"东南区", Status: "正在出售", Address: "捷达大路1777号", Description: "捷达大路1777号捷达大路1777号捷达大路1777号捷达大路1777号捷达大路1777号"},
                                { id: "1", Pic: "holder.js/200x185", Title: "万达广场真正的一室一厅，设施全，看好来电", Price: "500W", Area: "100", Bed: "1", Baths: "2", Carport: "2", District:"东南区", Status: "正在出售", Address: "捷达大路1777号", Description: "捷达大路1777号捷达大路1777号捷达大路1777号捷达大路1777号捷达大路1777号"},
                                { id: "1", Pic: "holder.js/200x185", Title: "万达广场真正的一室一厅，设施全，看好来电", Price: "500W", Area: "100", Bed: "1", Baths: "2", Carport: "2", District:"东南区", Status: "正在出售", Address: "捷达大路1777号", Description: "捷达大路1777号捷达大路1777号捷达大路1777号捷达大路1777号捷达大路1777号"},
                                { id: "1", Pic: "holder.js/200x185", Title: "万达广场真正的一室一厅，设施全，看好来电", Price: "500W", Area: "100", Bed: "1", Baths: "2", Carport: "2", District:"东南区", Status: "正在出售", Address: "捷达大路1777号", Description: "捷达大路1777号捷达大路1777号捷达大路1777号捷达大路1777号捷达大路1777号"}
                            ];
                            $("#houseSellTemplate").tmpl(houseData).appendTo("#houseSellBody");
                        </script>
                    </div>
                    <div class="tab-pane" id="panel-775800">
                        23
                    </div>
                </div>
            </div>
            <!--广告推广区-->
        </div>
        <div class="col-md-3">
            <div class="row">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            其他人也在看
                        </h3>
                    </div>
                    <div class="panel-body">
                        <img data-src="holder.js/300x240" class="img-rounded img-responsive">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            其他人也在看
                        </h3>
                    </div>
                    <div class="panel-body">
                        <img data-src="holder.js/300x240" class="img-rounded img-responsive">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            其他人也在看
                        </h3>
                    </div>
                    <div class="panel-body">
                        <img data-src="holder.js/300x240" class="img-rounded img-responsive">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>