package com.chinalife.servlet;

import com.chinalife.dao.TeamDAO;
import com.chinalife.dao.UserDAO;
import com.chinalife.servlet.chart.TeamPCQuery;
import com.chinalife.user.UserCategory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mf.dal.Converter;
import com.mf.dal.DAOException;
import com.mf.dal.DAOFacade;
import com.mf.dal.DBAccesser;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/**
 * Created by Chao.Cui.VWED on 14-9-18.
 */
public class TestDBSql {
    private static String log4jFileUbuntu = "D:\\00project\\mf\\mf-chinalife\\src\\test\\resources\\log4j.properties";
    private static String dbFileUbuntu = "D:\\00project\\mf\\mf-chinalife\\src\\test\\resources\\db_config.xml";

    public static void main(String[] str) throws SQLException {
//        testDB();
//        testJson();
        testJsonPerDay();
        /*String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";//SQL数据库引擎
        String connectDB="jdbc:sqlserver://10.120.78.71:1433;DatabaseName=master";//数据源

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
            String startDate = "2014-09-18 8:00";
            String endDate = "2014-09-19 8:00";
            List<TeamPCQuery.JsonTeamPC> productions = DAOFacade.getDAO(TeamDAO.class).queryDataPerDay(startDate,endDate,new Converter<TeamPCQuery.JsonTeamPC>() {
                @Override
                public TeamPCQuery.JsonTeamPC convert(ResultSet resultSet) throws SQLException {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    TeamPCQuery.JsonTeamPC pro = new TeamPCQuery.JsonTeamPC();
//                    pro.setStationId(resultSet.getString("station_id"));
//                    pro.setPlan(resultSet.getString("plan_output"));
                    pro.setActual(resultSet.getString("actual_output"));
//                    pro.setRoa(resultSet.getString("roa"));
//                    pro.setOpr(resultSet.getString("opr"));
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
}
