package com.chinalife.servlet;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Chao.Cui.VWED on 14-10-24.
 */
public class TestUtilTools {
    public static void main(String[] str) {
        testDateTrans();
    }

    public static void testDateTrans(){
        String origin = "2014-09-12 8:00";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        long millionEnd = 0;
        long millionStart = 0;
        try {
            millionEnd = sdf.parse(origin).getTime();//毫秒
            millionStart = millionEnd - 86400000;
            java.util.Date startDate = new java.util.Date(millionStart);
            System.out.println(startDate);
            String endDate = sdf.format(startDate);
            System.out.println(endDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
