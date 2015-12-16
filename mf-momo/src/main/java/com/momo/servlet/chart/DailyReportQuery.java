package com.momo.servlet.chart;

import com.momo.dao.DailyReportDAO;
import com.momo.bean.DailyReportJson;
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
import java.util.List;

/**
 * Created by Chao.Cui.VWED on 14-9-16.
 */
public class DailyReportQuery extends BaseServlet {
    private static final Logger logger = Logger.getLogger(ProductionMultiQuery.class);
    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter DailyReportQuery servlet.");

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();

            List<DailyReportJson> dailyReportsList = DAOFacade.getDAO(DailyReportDAO.class).queryDailyReport(new Converter<DailyReportJson>() {
                @Override
                public DailyReportJson convert(ResultSet resultSet) throws SQLException {
                    //设定显示时间格式 HH;mm 24小时  hh:mm 12小时
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    long millionSeconds = 0;

                    DailyReportJson temp = new DailyReportJson();
                    temp.setPlanG(resultSet.getString("plan_output").trim());
                    temp.setActualG(resultSet.getString("actual_output").trim());
                    String str = resultSet.getString("ProDate");

                    try {
                        millionSeconds = sdf.parse(str).getTime();//毫秒
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    temp.setDateG(str);
                    temp.setDateLong(millionSeconds);

                    return temp;
                }
            });
            ObjectMapper objectMapper = new ObjectMapper();
            String ret = objectMapper.writeValueAsString(dailyReportsList);
            objectMapper.writeValue(System.out, dailyReportsList);
            ret = "{\"items\":" + ret + "}";
            logger.error("Result : " + ret);

            printWriter.write(ret);

        } catch (DAOException e) {
            logger.error("Failed to query all houses.", e);
            throw new ServletException(e);
        }
    }


}
