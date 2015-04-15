package com.chinalife.servlet.chart;

import com.chinalife.dao.TeamDAO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mf.dal.Converter;
import com.mf.dal.DAOException;
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
public class TeamPCLiveQuery extends BaseServlet {
    private static final Logger logger = Logger.getLogger(TeamPCLiveQuery.class);
    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter TeamPCLiveQuery servlet.");

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
//            String startDate = "2014-10-28 11:50";
//            String endDate = "2014-10-28 11:53";
//            String strNumber = getParam(request, "num");
//            int number = Integer.valueOf(strNumber);
//            logger.error("number from web page  string : " + strNumber);
//            logger.error("number from web page int : " + strNumber);
            String startDate = getParam(request, "currentStartTime");
            String endDate = getParam(request, "currentEndTime");
            PrintWriter printWriter = response.getWriter();

            List<TeamPCQuery.JsonTeamPC> productions = DAOFacade.getDAO(TeamDAO.class).queryDataLive(startDate,endDate ,new Converter<TeamPCQuery.JsonTeamPC>() {
                @Override
                public TeamPCQuery.JsonTeamPC convert(ResultSet resultSet) throws SQLException {
                    long millionSeconds = 0;

                    //设定显示时间格式 HH;mm 24小时  hh:mm 12小时
                    SimpleDateFormat sdf_1=new SimpleDateFormat("yyyy-MM-dd HH:mm");

                    TeamPCQuery.JsonTeamPC pro = new TeamPCQuery.JsonTeamPC();
//                    pro.setStationId(resultSet.getString("station_id"));
                    pro.setPlan(resultSet.getString("plan_output"));
                    pro.setNO(resultSet.getString("NO"));
                    pro.setActual(resultSet.getString("actual_output"));

                    String roa = resultSet.getString("roa").trim();
                    if(roa.length() != 1 ){
                        pro.setRoa(roa.substring(0,roa.length()-1));
                    }
                    else{
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
            logger.error("Result : " + ret);

            printWriter.write(ret);
        } catch (DAOException e) {
            logger.error("Failed to query all houses.", e);
            throw new ServletException(e);
        }
    }
}
