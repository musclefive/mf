function NewDate(str) {
    str = str.split(' ');
    var years = str[0];
    var hours = str[1];
    var year = years.split('-');
    var hour = hours.split(':');
    var date = new Date();
    date.setFullYear(year[0], year[1] - 1, year[2]);
    date.setHours(hour[0], hour[1], 0, 0);
    return date;
}
//根据指定的时间格式 得到日期对象 在IE、 FF、Chrome下兼容
//str 格式 "2014-10-12 09:05"

//获取指定日期的时间格式 str = 2014-11-12 返回值2014-11-12 08:00
function getQueryTodayTimeSpecify(str){

    var hourandminute = "08:00";
    var today = new Date(str);
    var month = today.getMonth() + 1;
    var formatToday = today.getFullYear()+"-"+month+"-"+today.getDate() + " " + hourandminute;
    console.info("Method:getQueryTodayTimeSpecify()--formatToday :" + formatToday);

    return formatToday;
}
//根据毫秒数，获取格式化的时间 2014-11-24 08:55 ；如果获取当前时间
// longTime  = new Date().getTime())
function getFormatDate(longTime){
    var returnStr = "";
    var today;
    /*if( longTime == "")
    {
        //获取当前时间 格式2014-11-21 10:13
        today = new Date();

    }else{
        //获取指定时间格式日期
        today = new Date(parseInt(longTime));
    }*/
    today = new Date(parseInt(longTime));
    var month = today.getMonth() + 1;
    var hours = today.getHours();
    var minutes = today.getMinutes();
    if(hours < 10)
    {
        hours = "0" +hours;
    }
    if(minutes < 10)
    {
        minutes = "0" + minutes;
    }
    returnStr = today.getFullYear()+"-"+month+"-"+today.getDate() + " " + hours + ":" + minutes;
    return returnStr;
}
//根据毫秒数获得指定格式时间 2014-9-4 08:00 UTC
function getFormatUTCTime(longTime){
    var returnFormatTime = "";
    var currentTime = new Date(parseInt(longTime));
    var years = currentTime.getUTCFullYear();
    var months = currentTime.getUTCMonth();
    months = months + 1;
    var days = currentTime.getUTCDate();
    var minutes = currentTime.getUTCMinutes();
    var hours = currentTime.getUTCHours();
    if(minutes<=9){
        minutes="0"+minutes;
    }
    if(hours<=9){
        hours="0"+hours;
    }
    returnFormatTime = years + "-" + months + "-" + days + " " + hours +":" + minutes;
    return returnFormatTime;
}
//获取当前日期的时间格式 返回值2014-11-12 08:00
function getQueryTodayTime(){
    var hourandminute = "08:00";
    var today = new Date();
    var month = today.getMonth() + 1;
    var hours = today.getHours();
    var yesterday;
    var longToday  = Date.UTC(today.getFullYear(), today.getMonth(), today.getDate());
    if(hours < 8 && hours >=0){
        //表示已过凌晨
        yesterday = new Date(longToday - 86400000);

    }else{
        //没过凌晨12点 则日期
        yesterday = today;
    }
    var month_1 = yesterday.getMonth() + 1;

    var formatToday = yesterday.getFullYear()+"-"+month_1+"-"+yesterday.getDate() + " " + hourandminute;

    return formatToday;
}

//获取当前日期后一天的时间格式 返回值2014-11-13 08:00
function getQueryTomorrowTime(){
    var hourandminute = "08:00";
    var today = new Date();
    var month = today.getMonth() + 1;
    var hours = today.getHours();
    var formatToday = today.getFullYear()+"-"+month+"-"+today.getDate() + " " + hourandminute;
    var longToday  = Date.UTC(today.getFullYear(), today.getMonth(), today.getDate());
    var selectedTomorrow;
    if(hours < 8 && hours >=0){
     //表示已过凌晨
         selectedTomorrow = new Date(longToday);

     }else{
     //没过凌晨12点 则日期
        selectedTomorrow = new Date(longToday + 86400000);
     }
    var month_1 = selectedTomorrow.getMonth() + 1;
    var selectedFormatTomorrow = selectedTomorrow.getFullYear()+"-"+month_1+"-"+selectedTomorrow.getDate() +  " " + hourandminute;
    return selectedFormatTomorrow;
}
//获取指定日期后一天的时间格式 返回值2014-11-13 08:00
function getQueryTomorrowTimeSpecify(str){
    var hourandminute = "08:00";
    //var hourandminute = "23:59";

    var today = new Date(str);
    var month = today.getMonth() + 1;
    var hours = today.getHours();
    var longToday  = Date.UTC(today.getFullYear(), today.getMonth(), today.getDate());
    var selectedTomorrow = new Date(longToday + 86400000);
/*    if(hours <= 8 && hours >=0){
        //表示已过凌晨
        selectedTomorrow = new Date(longToday);

    }else{
        //没过凌晨12点 则日期
        selectedTomorrow = new Date(longToday + 86400000);
    }*/
    var month_1 = selectedTomorrow.getMonth() + 1;
    var selectedFormatTomorrow = selectedTomorrow.getFullYear()+"-"+month_1+"-"+selectedTomorrow.getDate() +  " " + hourandminute;
    console.info("Method:getQueryTomorrowTimeSpecify()--selectedFormatTomorrow :" + selectedFormatTomorrow);
    return selectedFormatTomorrow;
}
/*
 * 根据用户名从数据库获取用户信息
 * */
function getUserNameAndRole(userName){
    $.ajax({
        "url" : "/momo/queryDomainUser?method=user&userName="+userName,
        type:'post',
        dataType: "json",
        async:false,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        complete : function(xhr, textStatus){
            console.info("btn update ajax complete,textStatus:" + textStatus + " data:" + this.data);
        },
        error : function (xhr, textStatus){
            console.info("btn update ajax error,textStatus:" + textStatus);
        },
        success: function(data){
            var showName = data.items["name"];
            var userRole = data.items["userRole"];
            var userDescript = data.items["userDescript"];
            if(userRole == "1"){
                userDescript = "管理员";
            }else if(userRole == "2"){
                userDescript = "操作员";
            }else{
                userDescript = "普通用户";
            }
            $.cookie("userName",userName);
            $.cookie("showName",showName);
            $.cookie("userRole",userRole);
            $.cookie("userDescript",userDescript);
            console.info("ajax success " + data);

            var temp = $.cookie("userDescript") + " " + $.cookie("showName");
            console.info("ajax user info from cookie after query database" + temp + " " + $.cookie("userRole"));
            $("#divUserName").html(temp);
            menuRight($.cookie("userRole"));
        }
    });
}

/*
 * 根据userRole设置用户菜单选项
 * */
function menuRight(userRole){
    if(userRole == "1"){
        //管理员 所有页面都可见
        $("#liDashboard").show();
        $("#liDailyProduction").show();
        $("#liHistoryProduction").show();
        $("#liPlanManage").show();
        $("#liBreakQuery").show();
        $("#liShiftManage").show();
        $("#liUserList").show();
    }else if(userRole == "2"){
        //操作员 用户权限维护对其不可见 liUserList
        $("#liDashboard").show();
        $("#liDailyProduction").show();
        $("#liHistoryProduction").show();
        $("#liPlanManage").show();
        $("#liBreakQuery").show();
        $("#liShiftManage").show();
    }else {
        //普通用户 生产计划维护 班次维护 用户权限对其不可见
        $("#liDashboard").show();
        $("#liDailyProduction").show();
        $("#liHistoryProduction").show();
        $("#liBreakQuery").show();
    }
}




