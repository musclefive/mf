package com.chinalife.servlet;

import com.chinalife.dao.BreakdownDAO;
import com.chinalife.dao.DailyReportDAO;
import com.chinalife.dao.TeamDAO;
import com.chinalife.servlet.chart.BreakdownAdd;
import com.chinalife.servlet.chart.DailyReportQuery;
import com.chinalife.servlet.chart.TeamPCQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mf.dal.Converter;
import com.mf.dal.DAOFacade;
import com.mf.dal.DBAccesser;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

/**9
 * Created by Chao.Cui.VWED on 14-9-18.
 */
public class TestDBSql {
    private static String dbFileUbuntu = "D:\\project\\mf\\mf-chinalife\\src\\test\\resources\\db_config.xml";
    private static String log4jFileUbuntu= "D:\\project\\mf\\mf-chinalife\\src\\test\\resources\\log4j.properties";
    private static final Logger logger = Logger.getLogger(TestDBSql.class);

    public static void main(String[] str) throws SQLException {
//        testDB();
//        testJson();
//        testJsonPerDay();
//        testJsonLive();
        testJsonLivePart();
//        testHighStock();

       /* String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";//SQL数据库引擎
        String connectDB="jdbc:sqlserver://10.120.78.100:54914;DatabaseName=chart";//数据源

        try
        {
            Class.forName(JDriver);//加载数据库引擎，返回给定字符串名的类
        }catch(ClassNotFoundException e)
        {
            //e.printStackTrace();
            System.out.println("加载数据库引擎失败");
            System.exit(0);
        }
        System.out.println("数据库驱动成功");

        try
        {
            String user="root";
            String password="root";
            Connection con=DriverManager.getConnection(connectDB,user,password);//连接数据库对象
            System.out.println("连接数据库成功");
            Statement stmt=con.createStatement();//创建SQL命令对象

            //创建表
            System.out.println("开始创建表");
            String query="create table TABLE1(ID NCHAR(2),NAME NCHAR(10))";//创建表SQL语句
            stmt.executeUpdate(query);//执行SQL命令对象
            System.out.println("表创建成功");

            //输入数据
            System.out.println("开始插入数据");
            String a1="INSERT INTO TABLE1 VALUES('1','旭哥')";//插入数据SQL语句
            String a2="INSERT INTO TABLE1 VALUES('2','伟哥')";
            String a3="INSERT INTO TABLE1 VALUES('3','张哥')";
            stmt.executeUpdate(a1);//执行SQL命令对象
            stmt.executeUpdate(a2);
            stmt.executeUpdate(a3);
            System.out.println("插入数据成功");

            //读取数据
            System.out.println("开始读取数据");
            ResultSet rs=stmt.executeQuery("SELECT * FROM TABLE1");//返回SQL语句查询结果集(集合)
            //循环输出每一条记录
            while(rs.next())
            {
                //输出每个字段
                System.out.println(rs.getString("ID")+"\t"+rs.getString("NAME"));
            }
            System.out.println("读取完毕");

            //关闭连接
            stmt.close();//关闭命令对象连接
            con.close();//关闭数据库连接
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            //System.out.println("数据库连接错误");
            System.exit(0);
        }*/

    }


    public static void testDB(){
        PropertyConfigurator.configure(log4jFileUbuntu);

        File confFile = new File(dbFileUbuntu);
        Validate.isTrue(confFile.exists());

        Properties properties = new Properties();
        properties.put("max.total", 50);
        try {
            DBAccesser.createInstance(new FileInputStream(confFile), properties);

            TeamDAO teamDAO = DAOFacade.getDAO(TeamDAO.class);


            String test = teamDAO.queryTest();

            System.out.println("this is test :" + test);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testJson(){

        PropertyConfigurator.configure(log4jFileUbuntu);

        File confFile = new File(dbFileUbuntu);
        Validate.isTrue(confFile.exists());

        Properties properties = new Properties();
        properties.put("max.total", 50);

        try {
            DBAccesser.createInstance(new FileInputStream(confFile), properties);

            List<TeamPCQuery.JsonTeamPC> productions = DAOFacade.getDAO(TeamDAO.class).queryAllProduction(new Converter<TeamPCQuery.JsonTeamPC>() {
                @Override
                public TeamPCQuery.JsonTeamPC convert(ResultSet resultSet) throws SQLException {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    TeamPCQuery.JsonTeamPC pro = new TeamPCQuery.JsonTeamPC();
                    pro.setStationId(resultSet.getString("station_id"));
                    pro.setPlan(resultSet.getString("plan_output"));
                    pro.setActual(resultSet.getString("actual_output"));
                    pro.setRoa(resultSet.getString("roa"));
                    pro.setOpr(resultSet.getString("opr"));
//                    pro.setCreateTime(resultSet.getTimestamp("create_time", Calendar.getInstance()));
//                    pro.setCreateTime(resultSet.getTimestamp("create_time", Calendar.getInstance().setTimeInMillis(0);));
                    String str = sdf.format(resultSet.getTimestamp("catch_time", Calendar.getInstance()));
                    pro.setCatchTime(str);
                    return pro;
                }
            });

            ObjectMapper objectMapper = new ObjectMapper();
            String ret = objectMapper.writeValueAsString(productions);

            ret = "{\"items\":" + ret + "}";

            System.out.println(ret);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void testJsonPerDay(){
        PropertyConfigurator.configure(log4jFileUbuntu);

        File confFile = new File(dbFileUbuntu);
        Validate.isTrue(confFile.exists());

        Properties properties = new Properties();
        properties.put("max.total", 50);

        try {
            DBAccesser.createInstance(new FileInputStream(confFile), properties);
            String startDate = "2014-11-18 8:00";
            String endDate = "2014-11-19 8:00";
            String queryDay = "2014-11-18";
            List<TeamPCQuery.JsonTeamPC> productions = DAOFacade.getDAO(TeamDAO.class).queryDataPerDay(startDate,endDate,new Converter<TeamPCQuery.JsonTeamPC>() {
                @Override
                public TeamPCQuery.JsonTeamPC convert(ResultSet resultSet) throws SQLException {
                    //设定显示时间格式 HH;mm 24小时  hh:mm 12小时
//                    SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
                    SimpleDateFormat sdf_1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    long millionSeconds = 0;
                    TeamPCQuery.JsonTeamPC pro = new TeamPCQuery.JsonTeamPC();
//                    pro.setStationId(resultSet.getString("station_id"));
                    String roa = resultSet.getString("roa").trim();
                    if(roa.length() != 1 ){
                        roa = roa.substring(0,roa.length()-1);
                    }
                    else{
                        roa = "0";
                    }
                    String opr = resultSet.getString("opr").trim();
                    if(opr.length() != 1 ){
//                        pro.setOpr(opr.substring(0, opr.length() - 1));
                        //如果opr 大于120%， 则设置为100%
                        opr = opr.substring(0, opr.length() - 1);
                        if(Integer.valueOf(opr) > 120){
                            opr = "100";
                        }
                    }
                    else{
                        opr = "0";
                    }
//                    String str = sdf.format(resultSet.getTimestamp("catch_time", Calendar.getInstance()));
                    String str = sdf_1.format(resultSet.getTimestamp("catch_time", Calendar.getInstance()));
                    try {
                        sdf_1.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
                        millionSeconds = sdf_1.parse(str).getTime();//毫秒
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    pro.setCatchTime(str);
                    pro.setCatchTime(String.valueOf(millionSeconds));
                    pro.setPlan(resultSet.getString("plan_output"));
                    pro.setActual(resultSet.getString("actual_output"));
                    pro.setNO(resultSet.getString("NO"));
                    pro.setRoa(roa);
                    pro.setOpr(opr);
                    return pro;
                }
            });

            //查询当日所有的breakdown record
            List<BreakdownAdd.JsonBreakdown> breakdowns = DAOFacade.getDAO(BreakdownDAO.class).queryBreakdownPerDay(queryDay, new Converter<BreakdownAdd.JsonBreakdown>() {
                @Override
                public BreakdownAdd.JsonBreakdown convert(ResultSet resultSet) throws SQLException {

                    BreakdownAdd.JsonBreakdown record = new BreakdownAdd.JsonBreakdown();
                    String NO = resultSet.getString("No").trim();
                    String break_no = resultSet.getString("break_no").trim();
                    String break_level = resultSet.getString("break_level").trim();
                    String break_reason = resultSet.getString("break_reason").trim();
                    String break_type = resultSet.getString("break_type").trim();
                    String break_name = resultSet.getString("break_name").trim();
                    String start_time = resultSet.getString("start_time").trim();
                    String end_time = resultSet.getString("end_time").trim();
                    String duration = resultSet.getString("duration").trim();
                    long start_millionSeconds = 0;
                    long end_millionSeconds = 0;

                    //将时间转化成毫秒格式
                    SimpleDateFormat sdf_1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    try {
                        sdf_1.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
                        start_millionSeconds = sdf_1.parse(start_time).getTime();//毫秒
                        end_millionSeconds = sdf_1.parse(end_time).getTime();//毫秒

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    record.setNO(NO);
                    record.setBreakNO(break_no);
                    record.setBreakLevel(break_level);
                    record.setBreakReason(break_reason);
                    record.setBreakType(break_type);
                    record.setBreakName(break_name);
                    record.setStartTime(String.valueOf(start_millionSeconds));
                    record.setEndTime(String.valueOf(end_millionSeconds));
                    record.setDuration(duration);

                    return record;
                }
            });

            ObjectMapper objectMapper = new ObjectMapper();
            String ret = objectMapper.writeValueAsString(productions);

            String break_ret = objectMapper.writeValueAsString(breakdowns);
//            ret = "{\"items\":" + ret + "}";
            ret = "{\"items\":" + ret + ",\"records\":" + break_ret + "}";

            System.out.println(ret);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //测试获取一条数据
    public static void testJsonLive(){
        PropertyConfigurator.configure(log4jFileUbuntu);

        File confFile = new File(dbFileUbuntu);
        Validate.isTrue(confFile.exists());

        Properties properties = new Properties();
        properties.put("max.total", 50);

        try {
            DBAccesser.createInstance(new FileInputStream(confFile), properties);
            String strNumber = "182268";
            int number = Integer.valueOf(strNumber);
            TeamPCQuery.JsonTeamPC production = DAOFacade.getDAO(TeamDAO.class).querySingleData(number, new Converter<TeamPCQuery.JsonTeamPC>() {
                @Override
                public TeamPCQuery.JsonTeamPC convert(ResultSet resultSet) throws SQLException {
                    SimpleDateFormat sdf = new SimpleDateFormat("yy,MM,dd HH:mm");

                    TeamPCQuery.JsonTeamPC temp = new TeamPCQuery.JsonTeamPC();
                    temp.setNO(resultSet.getString("NO"));
                    temp.setActual(resultSet.getString("actual_output"));
                    temp.setPlan(resultSet.getString("plan_output"));
//                    System.out.println("actual output : " + resultSet.getString("actual_output"));
                    String roa = resultSet.getString("roa").trim();
                    if(roa.length() != 1 ){
                        temp.setRoa(roa.substring(0,roa.length()-1));
                    }
                    else{
                        temp.setRoa("0");
                    }

                    String opr = resultSet.getString("opr").trim();
                    temp.setOpr(opr.substring(0, opr.length() - 1));

                    String str = sdf.format(resultSet.getTimestamp("catch_time", Calendar.getInstance()));
                    temp.setCatchTime(str);
                    return temp;
                }
            });
            System.out.println("NO:" + production.getNO());
            ObjectMapper objectMapper = new ObjectMapper();
            String ret = objectMapper.writeValueAsString(production);

            ret = "{\"items\":" + ret + "}";

            System.out.println(ret);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据NO获得截止到某一条的数据
    public static void testJsonLivePart(){
        PropertyConfigurator.configure(log4jFileUbuntu);

        File confFile = new File(dbFileUbuntu);
        Validate.isTrue(confFile.exists());

        Properties properties = new Properties();
        properties.put("max.total", 50);

        String startDate = "2014-12-18 08:00";
        String endDate = "2014-12-18 15:38";
        String strNumber = "183213";
        int number = Integer.valueOf(strNumber);
        System.out.println("int and string :" + strNumber + "  " + number);
        try {
            DBAccesser.createInstance(new FileInputStream(confFile), properties);
            List<TeamPCQuery.JsonTeamPC> productions = DAOFacade.getDAO(TeamDAO.class).queryDataLive(startDate, endDate, new Converter<TeamPCQuery.JsonTeamPC>() {
                @Override
                public TeamPCQuery.JsonTeamPC convert(ResultSet resultSet) throws SQLException {
                    long millionSeconds = 0;
                    //设定显示时间格式 HH;mm 24小时  hh:mm 12小时
                    SimpleDateFormat sdf_1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    TeamPCQuery.JsonTeamPC pro = new TeamPCQuery.JsonTeamPC();
//                    pro.setStationId(resultSet.getString("station_id"));
                    pro.setPlan(resultSet.getString("plan_output"));
                    pro.setActual(resultSet.getString("actual_output"));
                    pro.setNO(resultSet.getString("NO"));

                    String roa = resultSet.getString("roa").trim();
                    if (roa.length() != 1) {
                        pro.setRoa(roa.substring(0, roa.length() - 1));
                    } else {
                        pro.setRoa("0");
                    }

                    String opr = resultSet.getString("opr").trim();
                    pro.setOpr(opr.substring(0, opr.length() - 1));
                    String str = sdf_1.format(resultSet.getTimestamp("catch_time", Calendar.getInstance()));
                    try {
//                        sdf_1.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
                        millionSeconds = sdf_1.parse(str).getTime();//毫秒
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    pro.setCatchTime(String.valueOf(millionSeconds));
                    return pro;
                }
            });
            ObjectMapper objectMapper = new ObjectMapper();
            String ret = objectMapper.writeValueAsString(productions);

            ret = "{\"items\":" + ret + "}";

            System.out.println(ret);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static void testHighStock(){
        PropertyConfigurator.configure(log4jFileUbuntu);

        File confFile = new File(dbFileUbuntu);
        Validate.isTrue(confFile.exists());

        Properties properties = new Properties();
        properties.put("max.total", 50);

        try {
            DBAccesser.createInstance(new FileInputStream(confFile), properties);
            List<DailyReportQuery.DailyReportJson> dailyReportsList = DAOFacade.getDAO(DailyReportDAO.class).queryDailyReport(new Converter<DailyReportQuery.DailyReportJson>() {
                @Override
                public DailyReportQuery.DailyReportJson convert(ResultSet resultSet) throws SQLException {
                    //设定显示时间格式 HH;mm 24小时  hh:mm 12小时
                    logger.error("enter queryDataPerDay : " + resultSet);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    long millionSeconds = 0;
                    DailyReportQuery.DailyReportJson temp = new DailyReportQuery.DailyReportJson();
//                    pro.setStationId(resultSet.getString("station_id"));
                    temp.setPlanG(resultSet.getString("plan_output").trim());
                    temp.setActualG(resultSet.getString("actual_output").trim());
//                    String str = sdf.format(resultSet.getTimestamp("ProDate", Calendar.getInstance()));
                    String str = resultSet.getString("ProDate");
                    try {
                        millionSeconds = sdf.parse(str).getTime();//毫秒
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    temp.setDateG(str);
                    temp.setDateLong(millionSeconds);
//                    System.out.println(millionSeconds);

//                    temp.setDateG(resultSet.getString("ProDate"));
                    return temp;
                }
            });
            ObjectMapper objectMapper = new ObjectMapper();
//            String ret = objectMapper.writeValueAsString(dailyReportsList);
//            String ret = objectMapper
            objectMapper.writeValue(System.out, dailyReportsList);

//            ret = "{\"items\":" + ret + "}";

//            System.out.println(ret);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
