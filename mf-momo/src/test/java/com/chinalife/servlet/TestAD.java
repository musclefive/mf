package com.chinalife.servlet;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Chao.Cui.VWED on 2015/12/7.
 */
public class TestAD {

    public static void main(String[] str) throws SQLException {
        System.out.println(System.getProperty("user.name"));
        System.out.println(System.getenv("USERNAME"));
        System.out.println(System.getenv("USERDOMAIN"));
//        System.ge
//        testTimeManage();
        testSQLDate();
    }

    public static void testTimeManage(){
        String shift_start_time = "20141111080000";
        String shift_end_time = "10:00:00";


        String   s1   =   "08:00:00";
        String   s2   =   "16:30:00";
        String   s3   =   "11:00:00";
        String   s4   =   "11:30:00";

        String   s7   =   "19:00:00";
        String   s8   =   "19:30:00";

        String   s9   =   "01:00:00";
        String   s10   =   "08:00:00";
        String   s11   =   "02:00:00";
        String   s12   =   "03:00:00";


        String strLimitTime = "01:00:00";
        // 12*60*60*1000 毫秒
        long twlveHour = 43200000;
        long longLimitTime;

        SimpleDateFormat   df   =   new   SimpleDateFormat("hh:mm:ss");
        Date begin= null;
        try {
            begin = df.parse(s1);
            Date   end   =   df.parse(s2);
            long   between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
            System.out.println(between);


//            longLimitTime = df.parse(strLimitTime).getTime();
//            System.out.println("longLimitTime:" + " " + longLimitTime);
            df.setTimeZone(TimeZone.getTimeZone("GMT"));

            String   s5   =   "23:00:00";
            String   s6   =   "08:00:00";
            String   s13   =   "22:00:00";
            String   s14   =   "23:00:00";
//            getBetween(df.parse(s1).getTime(), df.parse(s2).getTime());
            getBetween(df.parse(s5).getTime(), df.parse(s6).getTime());
            getBetween(df.parse(s13).getTime(), df.parse(s14).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static long getBetween(Long timeStart, Long timeEnd){
        Long returnVal = 0l;
        Long standard = 86400000l;

        if(timeEnd < timeStart){
            //结束时间为1点 开始时间为20点
            returnVal = standard  + timeEnd - timeStart;
        }else{
            returnVal = timeEnd - timeStart;
        }
        return returnVal;
    }

    public static void testSQLDate(){

        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");

        String today =  sim.format(rightNow.getTime());
//        java.util.Date c = new java.util.Date(today);
        java.sql.Date b = java.sql.Date.valueOf(today);
        System.out.println(b);
    }
}