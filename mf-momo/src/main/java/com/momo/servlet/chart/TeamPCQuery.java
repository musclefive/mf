package com.momo.servlet.chart;

import com.momo.dao.BreakdownDAO;
import com.momo.dao.TeamDAO;
import com.momo.bean.JsonBreakdown;
import com.momo.bean.JsonTeamPC;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mf.dal.Converter;
import com.mf.dal.DAOFacade;
import com.mf.util.servlet.BaseServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Chao.Cui.VWED on 14-9-16.
 */
public class TeamPCQuery extends BaseServlet {
    private static final Logger logger = Logger.getLogger(TeamPCQuery.class);
    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter TeamPCQuery servlet.");

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();
            String startDate = "2014-09-18 8:00";
            String endDate = "2014-09-19 8:00";
            String queryDay = "2014-09-18";

//            String tmp_startDate = getParam(request , "startDate");
            //tmp_startDate 为查询的起始日期 10-13， 结束时期10-14
//            String tmp_startDate = getParam(request , "endDate");
//
//            //处理时间 传入参数格式为2014-09-18 转化成 2014-09-18 8:00
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//            long millionStart = sdf.parse(tmp_startDate).getTime();//毫秒
//            long millionEnd = millionStart + 86400000;
//            String endDate = sdf.format(new java.util.Date(millionEnd));
//            String queryDay = tmp_startDate;
//            String startDate = tmp_startDate + " 8:00";
//            endDate = endDate + " 8:00";

            logger.error("query startDate:" + startDate + "  endDate" + endDate);
            //pic price area bed baths carport district status title address
            List<JsonTeamPC> productions = DAOFacade.getDAO(TeamDAO.class).queryDataPerDay(startDate,endDate,new Converter<JsonTeamPC>() {
                @Override
                public JsonTeamPC convert(ResultSet resultSet) throws SQLException {
                    //设定显示时间格式 HH;mm 24小时  hh:mm 12小时
//                    SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
                    SimpleDateFormat sdf_1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    long millionSeconds = 0;
                    JsonTeamPC pro = new JsonTeamPC();
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
                        sdf_1.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT = UTC 时间
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
            List<JsonBreakdown> breakdowns = DAOFacade.getDAO(BreakdownDAO.class).queryBreakdownPerDay(queryDay, new Converter<JsonBreakdown>() {
                @Override
                public JsonBreakdown convert(ResultSet resultSet) throws SQLException {

                    JsonBreakdown record = new JsonBreakdown();
                    String NO = resultSet.getString("No").trim();
                    String break_no = resultSet.getString("break_no").trim();
                    String break_level = resultSet.getString("break_level").trim();
                    String break_reason = resultSet.getString("break_reason").trim();
                    String break_type = resultSet.getString("break_type").trim();
                    String break_name = resultSet.getString("break_name").trim();
                    String start_time = resultSet.getString("start_time").trim();
                    String end_time = resultSet.getString("end_time").trim();
                    String duration = resultSet.getString("duration").trim();

                    //将时间转化成毫秒格式
                    long start_millionSeconds = 0;
                    long end_millionSeconds = 0;
                    SimpleDateFormat sdf_1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    try {
                        sdf_1.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT = UTC 时间
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

            logger.error("Result : " + ret);
            printWriter.write(ret);
        } catch (Exception  e) {
            logger.error("Failed to query all houses.", e);
            throw new ServletException(e);
        }
    }
}
