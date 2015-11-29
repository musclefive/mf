package com.chinalife.servlet;

import com.chinalife.dao.BreakdownDAO;
import com.chinalife.dao.TeamDAO;
import com.chinalife.servlet.chart.BreakdownQuery;
import com.chinalife.servlet.chart.TeamPCQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mf.dal.Converter;
import com.mf.dal.DAOFacade;
import com.mf.dal.DBAccesser;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

/**9
 * Created by Chao.Cui.VWED on 14-9-18.
 */
public class TestBreakdown {
    private static String dbFileUbuntu = "D:\\project\\mf\\mf-chinalife\\src\\test\\resources\\db_config.xml";
    private static String log4jFileUbuntu= "D:\\project\\mf\\mf-chinalife\\src\\test\\resources\\log4j.properties";

    public static void main(String[] str) throws SQLException {
//        testInsertBreakdown();
//        testDeleteBreakdown();
//        testQueryOneRecord();
        testQueryAll();
//        testUpdateFlag();
//        testGMTTime();
//        testUpdateBreakdown();
    }

    public static void testGMTTime(){
        String strDate = "2014-11-01 10:23";
        SimpleDateFormat sdf_1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");

        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        long millionSeconds = 0;
        long millionSeconds_1 = 0;

        try {
            millionSeconds = sdf_1.parse(strDate).getTime();//毫秒
            millionSeconds_1 = sdf.parse(strDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(millionSeconds + " " + millionSeconds_1);
    }

    public static void testInsertBreakdown(){
        String breakNO = "21474";
        String breakLevel = "2";
        String breakReason = "这是一个测试";
        String breakType = "3";
        String breakName = "机修停台";
        String startTime = "2014-10-28 15:35";
        String endTime = "2014-10-29 16:56";
        long start_millionSeconds = 0;
        long end_millionSeconds = 0;

        String duration = "25";
        long long_duration = 0;
        long recordId = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            start_millionSeconds = sdf.parse(startTime).getTime();//毫秒
            end_millionSeconds = sdf.parse(endTime).getTime();//毫秒
            long_duration =  end_millionSeconds - start_millionSeconds;

            System.out.println("time:" + start_millionSeconds + " " + end_millionSeconds + " " + long_duration);
            duration = String.valueOf(long_duration);

            PropertyConfigurator.configure(log4jFileUbuntu);
            File confFile = new File(dbFileUbuntu);
            Validate.isTrue(confFile.exists());
            Properties properties = new Properties();
            properties.put("max.total", 50);

            DBAccesser.createInstance(new FileInputStream(confFile), properties);
            recordId = DAOFacade.getDAO(BreakdownDAO.class).createBreakdownRecord(breakNO, breakLevel, breakReason, breakType,
                    breakName, startTime, endTime, duration,"2014");

            System.out.println("recordId:" + recordId);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(recordId);
    }

/*    public  static void testUpdateFlag(){
        String NO = "298530";
        String flag = "1";
        long recordId = 0;

        try {
            PropertyConfigurator.configure(log4jFileUbuntu);
            File confFile = new File(dbFileUbuntu);
            Validate.isTrue(confFile.exists());
            Properties properties = new Properties();
            properties.put("max.total", 50);

            DBAccesser.createInstance(new FileInputStream(confFile), properties);
            recordId = DAOFacade.getDAO(TeamDAO.class).updateFlag(flag, Integer.valueOf(NO));
            System.out.println("recordId:" + recordId);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(recordId);
    }*/


    public  static void testUpdateBreakdown(){
        String NO = "30";
        String breakNO = "21474";
        String breakLevel = "2";
        String breakReason = "这是一个测试";
        String breakType = "3";
        String breakName = "机修停台";
        String startTime = "2014-10-28 15:35";
        String endTime = "2014-10-29 16:56";
        long start_millionSeconds = 0;
        long end_millionSeconds = 0;

        String duration = "25";
        long long_duration = 0;
        long recordId = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            start_millionSeconds = sdf.parse(startTime).getTime();//毫秒
            end_millionSeconds = sdf.parse(endTime).getTime();//毫秒
            long_duration =  end_millionSeconds - start_millionSeconds;

            System.out.println("time:" + start_millionSeconds + " " + end_millionSeconds + " " + long_duration);
            duration = String.valueOf(long_duration);

            PropertyConfigurator.configure(log4jFileUbuntu);
            File confFile = new File(dbFileUbuntu);
            Validate.isTrue(confFile.exists());
            Properties properties = new Properties();
            properties.put("max.total", 50);

            DBAccesser.createInstance(new FileInputStream(confFile), properties);
            recordId = DAOFacade.getDAO(BreakdownDAO.class).updateBreakdownRecord(breakLevel, breakReason, breakType, breakName, startTime , endTime , duration , Integer.valueOf(NO));

            System.out.println("recordId:" + recordId);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(recordId);
    }

    public  static void testDeleteBreakdown(){
        String NO = "369";
        String breakLevel = "1";
        String breakReason = "test";
        String breakType = "2";
        String breakName = "故障停台";
        String startTime = "2014-10-28 09:25";
        String endTime = "2014-10-28 09:26";
        long start_millionSeconds = 0;
        long end_millionSeconds = 0;

        String duration = "25";
        long long_duration = 0;
        long recordId = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            start_millionSeconds = sdf.parse(startTime).getTime();//毫秒
            end_millionSeconds = sdf.parse(endTime).getTime();//毫秒
            long_duration =  end_millionSeconds - start_millionSeconds;

            System.out.println("time:" + start_millionSeconds + " " + end_millionSeconds + " " + long_duration);
            duration = String.valueOf(long_duration);

            PropertyConfigurator.configure(log4jFileUbuntu);
            File confFile = new File(dbFileUbuntu);
            Validate.isTrue(confFile.exists());
            Properties properties = new Properties();
            properties.put("max.total", 50);

            DBAccesser.createInstance(new FileInputStream(confFile), properties);
            recordId = DAOFacade.getDAO(BreakdownDAO.class).deleteSingleBreakdown(Integer.valueOf(NO));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(recordId);
    }

    public static void testQueryOneRecord(){
        String NO = "2";
        String breakLevel = "1";
        String breakReason = "test";
        String breakType = "2";
        String breakName = "故障停台";
        String startTime = "2014-10-28 09:25";
        String endTime = "2014-10-28 09:26";
        long start_millionSeconds = 0;
        long end_millionSeconds = 0;

        String duration = "25";
        long long_duration = 0;
        long recordId = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            start_millionSeconds = sdf.parse(startTime).getTime();//毫秒
            end_millionSeconds = sdf.parse(endTime).getTime();//毫秒
            long_duration =  end_millionSeconds - start_millionSeconds;

            System.out.println("time:" + start_millionSeconds + " " + end_millionSeconds + " " + long_duration);
            duration = String.valueOf(long_duration);

            PropertyConfigurator.configure(log4jFileUbuntu);
            File confFile = new File(dbFileUbuntu);
            Validate.isTrue(confFile.exists());
            Properties properties = new Properties();
            properties.put("max.total", 50);
            DBAccesser.createInstance(new FileInputStream(confFile), properties);
            BreakdownQuery.JsonBreakdown record = DAOFacade.getDAO(BreakdownDAO.class).querySingleBreakdown(Integer.valueOf(NO), new Converter<BreakdownQuery.JsonBreakdown>() {
                @Override
                public BreakdownQuery.JsonBreakdown convert(ResultSet resultSet) throws SQLException {
                    BreakdownQuery.JsonBreakdown temp = new BreakdownQuery.JsonBreakdown();
                    temp.setNO(String.valueOf(resultSet.getInt("NO")));
                    temp.setBreakLevel(resultSet.getString("break_level").trim());
                    temp.setBreakName(resultSet.getString("break_name").trim());
                    temp.setBreakReason(resultSet.getString("break_reason").trim());
                    temp.setBreakType(resultSet.getString("break_type").trim());
                    String temp_start_time = resultSet.getString("start_time").trim();
                    String temp_end_time =  resultSet.getString("end_time").trim();
                    if(temp_start_time.length() > 0 && temp_start_time.length() > 0){
                        temp_start_time = temp_start_time.substring(0, temp_start_time.length() - 5);
                        temp_end_time = temp_end_time.substring(0, temp_end_time.length() - 5);
                    }
                    temp.setStartTime(temp_start_time);
                    temp.setEndTime(temp_end_time);
                    temp.setDuration(resultSet.getString("duration").trim());
                    return temp;
                }
            });

            ObjectMapper objectMapper = new ObjectMapper();
            String ret = objectMapper.writeValueAsString(record);

            ret = "{\"items\":" + ret + "}";

            System.out.println(ret);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void testQueryAll(){
        String NO = "2";
        String breakLevel = "1";
        String breakReason = "test";
        String breakType = "2";
        String breakName = "故障停台";
        String startTime = "2014-10-28 09:25";
        String endTime = "2014-10-28 09:26";
        long start_millionSeconds = 0;
        long end_millionSeconds = 0;

        String duration = "25";
        long long_duration = 0;
        long recordId = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            start_millionSeconds = sdf.parse(startTime).getTime();//毫秒
            end_millionSeconds = sdf.parse(endTime).getTime();//毫秒
            long_duration =  end_millionSeconds - start_millionSeconds;

            System.out.println("time:" + start_millionSeconds + " " + end_millionSeconds + " " + long_duration);
            duration = String.valueOf(long_duration);

            PropertyConfigurator.configure(log4jFileUbuntu);
            File confFile = new File(dbFileUbuntu);
            Validate.isTrue(confFile.exists());
            Properties properties = new Properties();
            properties.put("max.total", 50);
            DBAccesser.createInstance(new FileInputStream(confFile), properties);
            List<BreakdownQuery.JsonBreakdown> records = DAOFacade.getDAO(BreakdownDAO.class).queryAllBreakdown(new Converter<BreakdownQuery.JsonBreakdown>() {
                @Override
                public BreakdownQuery.JsonBreakdown convert(ResultSet resultSet) throws SQLException {
                    BreakdownQuery.JsonBreakdown temp = new BreakdownQuery.JsonBreakdown();
                    temp.setNO(String.valueOf(resultSet.getInt("NO")));
                    temp.setBreakLevel(resultSet.getString("break_level").trim());
                    temp.setBreakName(resultSet.getString("break_name").trim());
                    temp.setBreakReason(resultSet.getString("break_reason").trim());
                    temp.setBreakType(resultSet.getString("break_type").trim());
                    String temp_start_time = resultSet.getString("start_time").trim();
                    String temp_end_time =  resultSet.getString("end_time").trim();
                    if(temp_start_time.length() > 0 && temp_start_time.length() > 0){
                        temp_start_time = temp_start_time.substring(0, temp_start_time.length() - 5);
                        temp_end_time = temp_end_time.substring(0, temp_end_time.length() - 5);
                    }
                    temp.setStartTime(temp_start_time);
                    temp.setEndTime(temp_end_time);
                    temp.setDuration(resultSet.getString("duration").trim());
                    return temp;
                }
            });

            ObjectMapper objectMapper = new ObjectMapper();
            String ret = objectMapper.writeValueAsString(records);

            ret = "{\"items\":" + ret + "}";

            System.out.println(ret);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateBreakdown(){

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
            String startDate = "2014-10-23 8:00";
            String endDate = "2014-10-22 8:00";
            List<TeamPCQuery.JsonTeamPC> productions = DAOFacade.getDAO(TeamDAO.class).queryDataPerDay(startDate,endDate,new Converter<TeamPCQuery.JsonTeamPC>() {
                @Override
                public TeamPCQuery.JsonTeamPC convert(ResultSet resultSet) throws SQLException {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    TeamPCQuery.JsonTeamPC pro = new TeamPCQuery.JsonTeamPC();
//                    pro.setStationId(resultSet.getString("station_id"));
                    pro.setPlan(resultSet.getString("plan_output"));
                    pro.setActual(resultSet.getString("actual_output"));

                    String roa = resultSet.getString("roa").trim();
                    if(roa.length() != 1 ){
                        pro.setRoa(roa.substring(0,roa.length()-1));
                    }
                    else{
                        pro.setRoa("0");
                    }
//                    pro.setRoa(resultSet.getString("roa").trim());

                    String opr = resultSet.getString("opr").trim();
                    pro.setOpr(opr.substring(0,opr.length()-1));
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

}
