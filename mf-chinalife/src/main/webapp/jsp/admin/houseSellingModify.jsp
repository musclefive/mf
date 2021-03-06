<%--
  Created by IntelliJ IDEA.
  User: Chao.Cui.VWED
  Date: 14-4-22
  Time: 上午11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>House Edit</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <c:import url="/template/style.html" />
    <c:import url="/template/js.html" />
    <c:import url="/template/import-jquery-file-upload.html" />
    <link rel="stylesheet" type="text/css" href="/mf-chinalife/css/prettify.css"/>
    <link rel="stylesheet" type="text/css" href="/mf-chinalife/css/bootstrap-wysihtml5.css"/>

    <style type="text/css" media="screen">
        .btn.jumbo {
            font-size: 20px;
            font-weight: normal;
            padding: 14px 24px;
            margin-right: 10px;
            -webkit-border-radius: 6px;
            -moz-border-radius: 6px;
            border-radius: 6px;
        }
    </style>
    <script src="/mf-chinalife/js/textarea/wysihtml5-0.3.0.js"></script>
    <script src="/mf-chinalife/js/textarea/prettify.js"></script>
    <script src="/mf-chinalife/js/textarea/bootstrap-wysihtml.js"></script>
    <script type="application/javascript" language="JavaScript">
        $(function () {
            // validate the comment form when it is submitted
            //$("#commentForm").validate();
//            $("#formHouseAdd").validate({
//                rules: {
//                    title: {
//                        required: true,
//                        minlength: 6
//                    },
//                    selectDistrict: "required",
//                    selectStatus: "required",
//                    address: {
//                        required: true,
//                        minlength: 6
//                    },
//                    selectType: "required",
//                    room: {
//                        required: true,
//                        digits: true
//                    },
//                    toilet: {
//                        required: true,
//                        digits: true
//                    },
//                    carport: {
//                        required: true,
//                        digits: true
//                    }
//                },
//
//                messages: {
//                    title: {
//                        required: "Please enter the title",
//                        minlength: "you need to text at least 6 chacaters",
//                        selectStatus: "you need to enter the status of the house"
//                    },
//                    selectStatus: "you need to enter the status of the house",
//                    address: {
//                        required: "Please enter the address",
//                        minlength: "you need to text at least 6 chacaters"
//                    },
//                    selectType: "you need to select a status for the house",
//                    room: {
//                        required: "Please provide a password",
//                        digits: "you need input a digit"
//                    },
//                    toilet: {
//                        required: "Please provide a password",
//                        digits: "you need input a digit"
//                    },
//                    carport: {
//                        required: "Please provide a password",
//                        digits: "you need input a digit"
//                    }
////                    debug : true
////                    confirm_password: {
////                        required: "Please provide a password",
////                        minlength: "Your password must be at least 5 characters long",
////                        equalTo: "Please enter the same password as above"
////                    },
////                    email: "Please enter a valid email address",
////                    agree: "Please accept our policy"
//                },
//                //设置错误信息显示的位置
//                errorPlacement: function (error, element) {
//                    //alert(error+":"+element.);
//                    if (element.is(":radio"))
//                        error.appendTo(element.parent().next().next());
//                    else if (element.is(":checkbox"))
//                        error.appendTo(element.parent().next());
//                    else
//                        error.appendTo(element.parent());
//                }
//            });

            /*获取house 对象，为每一个表单元素赋值*/
            $("#title").val("<c:out value="${requestScope.house.getTitle()}">未添加标题</c:out>");
            $("#selectDistrict option[value='<c:out value="${requestScope.house.getDistrict()}"/>']").prop("selected",true);
            $("#selectStatus option[value='<c:out value="${requestScope.house.getStatus()}"/>']").prop("selected",true);
            $("#address").val("<c:out value="${requestScope.house.getAddress()}">未添加地址信息</c:out>");
            $("#selectType option[value='<c:out value="${requestScope.house.getType()}"/>']").prop("selected",true);
            $("#room").val("<c:out value="${requestScope.house.getRoom()}"></c:out>");
            $("#toilet").val("<c:out value="${requestScope.house.getToilet()}"></c:out>");
            $("#carport").val("<c:out value="${requestScope.house.getCarport()}"></c:out>");
            $("#inputForArea").val("<c:out value="${requestScope.house.getArea()}"></c:out>");
            $("#low_price").val("<c:out value="${requestScope.house.getMinPrice()}"></c:out>");
            $("#high_price").val("<c:out value="${requestScope.house.getMaxPrice()}"></c:out>");
            <%--alert("<c:out escapeXml='false' value="${requestScope.house.getDescription()}" />");--%>
            /*excapeXML = false 转义&gt; 为html标签*/
            var htmlStr = "<c:out escapeXml='false' value="${requestScope.house.getDescription()}" />";
            $("#inputForDetail").html(htmlStr);
            $("#first_contact_name").val("<c:out value="${requestScope.house.getContactPerson1()}"></c:out>");
            $("#first_contact_phone").val("<c:out value="${requestScope.house.getContactPhone1()}"></c:out>");
            $("#second_contact_name").val("<c:out value="${requestScope.house.getContactPerson2()}"></c:out>");
            $("#second_contact_phone").val("<c:out value="${requestScope.house.getContactPhone2()}"></c:out>");


            $('#fileupload').fileupload({
                url: '/mf-chinalife/upload',
                maxFileSize: 3000000,
                acceptFileTypes:  /(\.|\/)(gif|jpe?g|png)$/i
            });
            $('#fileupload').bind('fileuploaddone', function (e, result) {
                $.each(result.result.files, function (index, file) {
                    $("#divForFileUpload").append("<input type='hidden' value='" + file.path + "' name='upload' id='"+file.id+"'>");
                });
                addImageUrl();
            });

            /*room toilet carpot 控件*/
            $('.btn-number').click(function(e){
                e.preventDefault();

                fieldName = $(this).attr('data-field');
                type      = $(this).attr('data-type');
                var input = $("input[name='"+fieldName+"']");
                var currentVal = parseInt(input.val());
                if (!isNaN(currentVal)) {
                    if(type == 'minus') {

                        if(currentVal > input.attr('min')) {
                            input.val(currentVal - 1).change();
                        }
                        if(parseInt(input.val()) == input.attr('min')) {
                            $(this).attr('disabled', true);
                        }

                    } else if(type == 'plus') {

                        if(currentVal < input.attr('max')) {
                            input.val(currentVal + 1).change();
                        }
                        if(parseInt(input.val()) == input.attr('max')) {
                            $(this).attr('disabled', true);
                        }

                    }
                } else {
                    input.val(0);
                }
            });
            $('.input-number').focusin(function(){
                $(this).data('oldValue', $(this).val());
            });
            $('.input-number').change(function() {

                minValue =  parseInt($(this).attr('min'));
                maxValue =  parseInt($(this).attr('max'));
                valueCurrent = parseInt($(this).val());

                name = $(this).attr('name');
                if(valueCurrent >= minValue) {
                    $(".btn-number[data-type='minus'][data-field='"+name+"']").removeAttr('disabled')
                } else {
                    alert('Sorry, the minimum value was reached');
                    $(this).val($(this).data('oldValue'));
                }
                if(valueCurrent <= maxValue) {
                    $(".btn-number[data-type='plus'][data-field='"+name+"']").removeAttr('disabled')
                } else {
                    alert('Sorry, the maximum value was reached');
                    $(this).val($(this).data('oldValue'));
                }


            });
            $(".input-number").keydown(function (e) {
                // Allow: backspace, delete, tab, escape, enter and .
                if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 190]) !== -1 ||
                    // Allow: Ctrl+A
                        (e.keyCode == 65 && e.ctrlKey === true) ||
                    // Allow: home, end, left, right
                        (e.keyCode >= 35 && e.keyCode <= 39)) {
                    // let it happen, don't do anything
                    return;
                }
                // Ensure that it is a number and stop the keypress
                if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
                    e.preventDefault();
                }
            });
        })
        function deleteAction(fileID){
            //如果选择全选删除按钮
            var selectedID = "";
            if(fileID == "all"){
                if(true == $("#deleteAll").prop("checked")){
                    $("#divForFileUpload").empty();
                }else{
                    //遍历checkbox列表，删除所选中的checkbox
                    $(".toggle").each(function(){
                        if ($(this).prop('checked') == true) {
                            selectedID = "#" + $(this).val();
                            $(selectedID).remove();
                        }
                    })
                }
            }
            //如果点击某一个删除按钮
            else{
                selectedID = "#"+fileID;
                $(selectedID).remove();
            }
            //点击删除后重新获取hidden字符串的图片地址
            addImageUrl();
        }
        /*获取所有input hidden 的图片地址，拼接字符串*/
        function addImageUrl(){
            var strSavedFiles = "";
            $("#divForFileUpload input:hidden").each(function(index){
//               alert("index:"+index+ "  value:"+ $(this).val());
                strSavedFiles += $(this).val();
                strSavedFiles += ";";
            });
//            strSavedFiles = strSavedFiles.substring(0,strSavedFiles.length-1);
            $("#saved_files").val(strSavedFiles);
        }
    </script>
</head>

<body>
<!--navigation for left area-->
<%@ include file="/template/header.jsp" %>
<div class="container">
<div class="row clearfix">
<div class="col-md-3">
    <%@ include file="/template/left.jsp" %>
</div>
<div class="col-md-9">
<div>
<form class="form-horizontal" id="formHouseModify" method="post" action="">
    <div id="legend" class="">
        <legend class="">House Modify</legend>
        <c:out escapeXml='false' value="${requestScope.house.getDescription()}" />
    </div>
    <%--div for title--%>
    <div class="form-group">
        <label class="col-md-2 control-label" for="title">标题</label>

        <div class="col-md-6 controls">
            <input placeholder="请填写标题" class="form-control" id="title" type="text"
                   name="title" value="">
        </div>
        <div class="col-md-4"></div>
    </div>
    <%--div for district--%>
    <div class="form-group">
        <label class="col-md-2 control-label">区域选择</label>

        <div class="col-md-4 controls">
            <select id="selectDistrict" name="district" class="form-control">
                <option value="">请选择</option>
                <option value="central">市中心</option>
                <option value="southEast">东南区</option>
                <option value="north">北区</option>
                <option value="northEast">东北区</option>
                <option value="east">东区</option>
                <option value="south">南区</option>
                <option value="west">西区</option>
                <option value="northWest">西北区</option>
                <option value="southWest">西南区</option>
            </select>
        </div>
        <div class="col-md-6"></div>
    </div>
    <div class="form-group">
        <label class="col-md-2 control-label">房屋状态</label>

        <div class="col-md-4 controls">
            <select class="form-control" id="selectStatus" name="status">
                <option value="">请选择</option>
                <option value="onSaling">在售</option>
                <option value="onAuction">拍卖</option>
                <option value="onSigning">正在签约</option>
                <option value="onSaled">出售</option>
            </select>
        </div>
        <div class="col-md-6"></div>
    </div>
    <%--div for house address--%>
    <div class="form-group">
        <label class="col-md-2 control-label" for="address">房屋地址</label>

        <div class="col-md-8 controls">
            <input placeholder="请输入房屋地址" class="form-control" id="address" type="text"
                   name="address">
        </div>
        <div class="col-md-2"></div>
    </div>
    <%--div for house type--%>
    <div class="form-group">
        <label class="col-md-2 control-label" for="selectType">房屋类型</label>

        <div class="col-md-6 controls">
            <select class="form-control" id="selectType" name="type">
                <option value="">请选择</option>
                <option value="House">房子House</option>
                <option value="Apartmen">公寓Apartment</option>
                <option value="Unit">单位Unit</option>
                <option value="Townhouse">联排别墅Townhouse</option>
                <option value="Land">土地Land</option>
                <option value="Commercial estate">商业地产Commercial estate</option>
                <option value="Others">其他Others</option>
            </select>
        </div>
        <div class="col-md-2"></div>
    </div>
    <%--div for house status--%>
    <%--<div class="form-group">--%>
        <%--<label class="col-md-2 control-label">房屋情况</label>--%>

        <%--<div class="col-md-6 controls">--%>
            <%--<div class="row">--%>
                <%--<div class="col-xs-2">--%>
                    <%--<input type="text" class="form-control" id="room" placeholder=""--%>
                           <%--name="room">--%>
                <%--</div>--%>
                <%--<div class="col-xs-1">--%>
                    <%--<label class="control-label" for="room">室</label>--%>
                <%--</div>--%>
                <%--<div class="col-xs-2">--%>
                    <%--<input type="text" class="form-control" id="toilet" placeholder=""--%>
                           <%--name="toilet">--%>
                <%--</div>--%>
                <%--<div class="col-xs-1">--%>
                    <%--<label class="control-label" for="toilet">卫</label>--%>
                <%--</div>--%>
                <%--<div class="col-xs-2">--%>
                    <%--<input type="text" class="form-control" id="carport" placeholder=""--%>
                           <%--name="carport">--%>
                <%--</div>--%>
                <%--<div class="col-xs=3">--%>
                    <%--<label class="control-label" for="carport">车库</label>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="col-md-4"></div>--%>
    <%--</div>--%>
    <div class="form-group">
        <label class="col-md-2 control-label">房屋情况</label>
        <div class="col-md-8 controls">
            <div class="input-group">
                              <span class="input-group-btn">
                                  <button type="button" class="btn btn-danger btn-number"  data-type="minus" data-field="houseType_shi">
                                      <span class="glyphicon glyphicon-minus"></span>
                                  </button>
                              </span>
                <input type="text" id="houseType_shi" name="houseType_shi" class="form-control input-number" value="1" min="1" max="9">
                              <span class="input-group-btn">
                                  <button type="button" class="btn btn-success btn-number" data-type="plus" data-field="houseType_shi">
                                      <span class="glyphicon glyphicon-plus"></span>
                                  </button>
                              </span>
                <label class="control-label" for="houseType_shi">&nbsp;&nbsp;&nbsp;&nbsp;室&nbsp;&nbsp;&nbsp;&nbsp;</label>
                              <span class="input-group-btn">
                                  <button type="button" class="btn btn-danger btn-number"  data-type="minus" data-field="houseType_wei">
                                      <span class="glyphicon glyphicon-minus"></span>
                                  </button>
                              </span>
                <input type="text" id="houseType_wei" name="houseType_wei" class="form-control input-number" value="1" min="1" max="9">
                              <span class="input-group-btn">
                                  <button type="button" class="btn btn-success btn-number" data-type="plus" data-field="houseType_wei">
                                      <span class="glyphicon glyphicon-plus"></span>
                                  </button>
                              </span>
                <label class="control-label" for="houseType_wei">&nbsp;&nbsp;&nbsp;&nbsp;卫&nbsp;&nbsp;&nbsp;&nbsp;</label>
                              <span class="input-group-btn">
                                  <button type="button" class="btn btn-danger btn-number"  data-type="minus" data-field="houseType_garage">
                                      <span class="glyphicon glyphicon-minus"></span>
                                  </button>
                              </span>
                <input type="text" id="houseType_garage" name="houseType_garage" class="form-control input-number" value="1" min="1" max="9">
                              <span class="input-group-btn">
                                  <button type="button" class="btn btn-success btn-number" data-type="plus" data-field="houseType_garage">
                                      <span class="glyphicon glyphicon-plus"></span>
                                  </button>
                              </span>
                <label class="control-label" for="houseType_garage">&nbsp;&nbsp;&nbsp;&nbsp;车</label>
            </div>

        </div>
    </div>
    <%--div for house area--%>
    <div class="form-group">
        <label class="col-md-2 control-label" for="inputForArea">面积</label>

        <div class="col-md-2 controls">
            <input placeholder="placeholder" class="form-control" id="inputForArea" type="text"
                   name="area">
        </div>
        <div class="col-md-2">
            <label class="control-label" for="inputForArea">平方米</label>
        </div>
        <div class="col-md-6"></div>
    </div>
    <%--div for high price and low price--%>
    <div class="form-group">
        <label class="col-md-2 control-label" for="low_price">售价</label>

        <div class="col-md-2 controls">
            <input placeholder="最低售价" class="form-control" id="low_price" type="text"
                   name="min_price">
        </div>
        <div class="col-xs-2">
            <label class="control-label" for="low_price">万</label>
        </div>
        <div class="col-md-2 controls">
            <input placeholder="最高售价" class="form-control" id="high_price" type="text"
                   name="max_price">
        </div>
        <div class="col-xs-2">
            <label class="control-label" for="high_price">万</label>
        </div>
        <div class="col-md-4"></div>
    </div>
    <%--div for house description textarea--%>
    <div class="form-group">
        <label class="col-md-2 control-label" for="inputForDetail">描述</label>

        <div class="col-md-10 controls">
            <div class="textarea">
                <%--<textarea class="form-control" id="inputForDetail" rows="6"--%>
                <%--name="description"> </textarea>--%>
                <textarea class="textarea"  id="inputForDetail" name="description"
                          placeholder="Enter text ..." style="width: 600px; height: 200px">
                </textarea>
                <script>
                    $('#inputForDetail').wysihtml5({
                        "font-styles": true, //Font styling, e.g. h1, h2, etc. Default true
                        "emphasis": true, //Italics, bold, etc. Default true
                        "lists": true, //(Un)ordered lists, e.g. Bullets, Numbers. Default true
                        "html": false, //Button which allows you to edit the generated HTML. Default false
                        "link": true, //Button to insert a link. Default true
                        "image": false, //Button to insert an image. Default true,
                        "color": false //Button to change color of font
                    });
                </script>
            </div>
        </div>
    </div>
    <%--div for first contact--%>
    <div class="form-group">
        <label class="col-md-2 control-label" for="first_contact_name">第一联系人</label>

        <div class="col-md-3 controls">
            <input placeholder="联系人姓名" class="form-control" id="first_contact_name" type="text"
                   name="first_contact_name">
        </div>
        <div class="col-md-3 controls pull-left">
            <input placeholder="联系人电话" class="form-control" id="first_contact_phone" type="text"
                   name="first_contact_phone">
        </div>
        <div class="col-md-4"></div>
    </div>
    <%--div for second contact--%>
    <div class="form-group">
        <label class="col-md-2 control-label" for="second_contact_name">第二联系人</label>

        <div class="col-md-3 controls">
            <input placeholder="联系人姓名" class="form-control" id="second_contact_name" type="text"
                   name="second_contact_name">
        </div>
        <div class="col-md-3">
            <input placeholder="联系人电话" class="form-control" id="second_contact_phone" type="text"
                   name="second_contact_phone">
        </div>
        <div class="col-md-4"></div>
    </div>
    <%--div for file upload input hidden--%>
    <div class="form-group" id="divForFileUpload">
    </div>
    <%--存放所有图片的url地址--%>
    <input name="saved_files" id="saved_files" value="" type="hidden"/>
    <%--div for submit button--%>
    <div class="form-group">
        <label class="col-md-2 control-label"></label>

        <div class="col-md-6 controls">
            <button type="submit" class="btn btn-primary" >Submit</button>
        </div>
        <div class="col-md-4"></div>
    </div>
</form>
<%--upload template--%>
<form id="fileupload" action="/mf-chinalife/upload" method="POST" enctype="multipart/form-data" style="height:300">
    <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
    <div id="legend_1" class="">
        <legend class="">上传图片</legend>
    </div>
    <label class="col-md-2 control-label"></label>
    <div class="row fileupload-buttonbar">
        <div class="col-md-10">
            <!-- The fileinput-button span is used to style the file input field as button -->
                <span class="btn btn-success fileinput-button">
                    <i class="glyphicon glyphicon-plus"></i>
                    <span>Add files...</span>
                    <input type="file" name="files[]" multiple>
                </span>
            <button type="submit" class="btn btn-primary start">
                <i class="glyphicon glyphicon-upload"></i>
                <span>Start upload</span>
            </button>
            <button type="reset" class="btn btn-warning cancel">
                <i class="glyphicon glyphicon-ban-circle"></i>
                <span>Cancel upload</span>
            </button>
            <button type="button" class="btn btn-danger delete" onclick="deleteAction('all');">
                <i class="glyphicon glyphicon-trash"></i>
                <span>Delete</span>
            </button>
            <input type="checkbox" class="toggle">
            <!-- The global file processing state -->
            <span class="fileupload-process"></span>
        </div>
        <!-- The global progress state -->
        <div class="col-lg-5 fileupload-progress fade">
            <!-- The global progress bar -->
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                <div class="progress-bar progress-bar-success" style="width:0%;"></div>
            </div>
            <!-- The extended global progress state -->
            <div class="progress-extended">&nbsp;</div>
        </div>
    </div>
    <!-- The table listing the files available for upload/download -->
    <table role="presentation" class="table table-striped">
        <tbody class="files"></tbody>
    </table>

</form>
</div>
</div>
</div>
</div>
<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start" disabled>
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>Start</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}" width="80" height="60"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete" onclick="deleteAction('{%=file.id%}');" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" name="delete" value="{%=file.size%}" class="toggle">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
</body>
</html>
