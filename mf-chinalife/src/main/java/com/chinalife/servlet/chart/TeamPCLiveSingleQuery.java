package com.chinalife.servlet.chart;

import com.chinalife.dao.TeamDAO;
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

/**
 * Created by Chao.Cui.VWED on 14-9-16.
 */
public class TeamPCLiveSingleQuery extends BaseServlet {
    private static final Logger logger = Logger.getLogger(TeamPCLiveSingleQuery.class);
    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter TeamPCLiveSingleQuery servlet.");

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
//            String startDate = "2014-09-18 8:00";
//            String endDate = "2014-09-19 8:00";
            String strNumber = getParam(request, "num");
//            strNumber = "1111111111";
            int number = Integer.valueOf(strNumber);

            PrintWriter printWriter = response.getWriter();

            TeamPCQuery.JsonTeamPC production = DAOFacade.getDAO(TeamDAO.class).querySingleData(number, new Converter<TeamPCQuery.JsonTeamPC>() {
                @Override
                public TeamPCQuery.JsonTeamPC convert(ResultSet resultSet) throws SQLException {
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    long millionSeconds = 0;
                    TeamPCQuery.JsonTeamPC temp = new TeamPCQuery.JsonTeamPC();
                    temp.setNO(resultSet.getString("NO"));
                    temp.setActual(resultSet.getString("actual_output"));
//                     temp.setActual("68");
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
                    try {
//                        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
                        millionSeconds = sdf.parse(str).getTime();//毫秒
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    temp.setCatchTime("1416459127853");
                    temp.setCatchTime(String.valueOf(millionSeconds));
                    return temp;
                }
            });

//            if(production)

            ObjectMapper objectMapper = new ObjectMapper();
            String ret = "";

            if(production == null){
                //当前没有数据
                ret = objectMapper.writeValueAsString("");
//                logger.error("production is null : " + ret);
            }else{
                ret = objectMapper.writeValueAsString(production);
            }
//            ret = "{\"items\":" + ret + "}";
            logger.error("Result : " + ret);

            printWriter.write(ret);
        } catch (DAOException e) {
            logger.error("Failed to query teampc for live one single query.", e);
            throw new ServletException(e);
        }
    }
}
