package com.momo.servlet.chart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mf.dal.Converter;
import com.mf.dal.DAOException;
import com.mf.dal.DAOFacade;
import com.mf.util.servlet.BaseServlet;
import com.momo.bean.Failure;
import com.momo.bean.JsonBreakdown;
import com.momo.dao.BreakdownDAO;
import com.momo.dao.FailureDAO;
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
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Chao.Cui.VWED on 14-9-16.
 */
public class FailureQuery extends BaseServlet {
    private static final Logger logger = Logger.getLogger(FailureQuery.class);
    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter FailureQuery servlet.");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String startDate = getParam(request, "startDate");
        String endDate = getParam(request, "endDate");

        queryBreakInfo(startDate,endDate, request,response);
    }

    /*
    * 查询所在时间范围的停台事件
    * String startDay 开始时间
    * String endDay 结束时间
    * */
    public void queryBreakInfo(String startDate, String endDate, HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException{
        logger.info("Enter FailureQuery servlet for query All Failure Record. startDate:" + startDate + " endDate:" + endDate);

        PrintWriter printWriter = response.getWriter();
        try {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            List<Failure> failureList = DAOFacade.getDAO(FailureDAO.class).queryFailureRecordByDate(startDate, endDate, new Converter<Failure>() {
                @Override
                public Failure convert(ResultSet resultSet) throws SQLException {
                    Failure record = new Failure();
                    String NO = resultSet.getString("No").trim();
                    String start_time = resultSet.getString("starttime").trim();
                    String finish_time = resultSet.getString("finishtime").trim();
                    SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long millionSeconds_start = 0;
                    long millionSeconds_end = 0;
                    long duration = 0;


                    if (resultSet.getString("station") != null) {
                        record.setStation(resultSet.getString("station").trim());
                    } else {
                        record.setStation(" ");
                    }
                    if (resultSet.getString("shift") != null) {
                        record.setShift(resultSet.getString("shift").trim());
                    } else {
                        record.setShift(" ");
                    }

                    try {
                        sdf_1.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT = UTC 时间
                        millionSeconds_start = sdf_1.parse(start_time).getTime();//毫秒
                        millionSeconds_end = sdf_1.parse(finish_time).getTime();//毫秒
                        duration = millionSeconds_end - millionSeconds_start;
//                        logger.info("duration : " + duration + " millionSeconds_start:" +start_time + ":" + millionSeconds_start + " millionSeconds_end:" + finish_time + ":"+ millionSeconds_end);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    record.setNo(Long.valueOf(NO));
                    record.setStart_time(start_time);
                    record.setFinish_time(finish_time);
                    record.setDuration(duration);
                    return record;
                }
            });

            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            String ret = objectMapper.writeValueAsString(failureList);
            ret = "{\"items\":" + ret + "}";
            printWriter.write(ret);

            logger.info("Result : " + ret);
        } catch (Exception  e) {
            logger.error("Failed to add breakdown record.", e);
        }
    }
}
