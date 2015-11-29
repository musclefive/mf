package com.momo.servlet.chart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mf.util.servlet.BaseServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Chao.Cui.VWED on 14-9-16.
 */
public class BreakdownQuery extends BaseServlet {
    private static final Logger logger = Logger.getLogger(BreakdownQuery.class);
    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter BreakdownQuery servlet.");

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();
            String tmp_endDate = getParam(request , "endDate");

            //处理时间 传入参数格式为2014-09-18 转化成 2014-09-18 8:00
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            long millionEnd = sdf.parse(tmp_endDate).getTime();//毫秒
            long millionStart = millionEnd - 86400000;
            String startDate = sdf.format(new Date(millionStart));
            String endDate = tmp_endDate + " 8:00";
            startDate = startDate + " 8:00";

            logger.error("startDate:" + startDate + "  endDate" + endDate);


        } catch (Exception  e) {
            logger.error("Failed to query all houses.", e);
            throw new ServletException(e);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class JsonBreakdown {
        private String NO;
        private String breakLevel;
        private String breakReason;
        private String breakType;
        private String breakName;
        private String startTime;
        private String endTime;
        private String duration;

        public String getNO() {
            return NO;
        }

        public void setNO(String NO) {
            this.NO = NO;
        }

        public String getBreakLevel() {
            return breakLevel;
        }

        public void setBreakLevel(String breakLevel) {
            this.breakLevel = breakLevel;
        }

        public String getBreakReason() {
            return breakReason;
        }

        public void setBreakReason(String breakReason) {
            this.breakReason = breakReason;
        }

        public String getBreakType() {
            return breakType;
        }

        public void setBreakType(String breakType) {
            this.breakType = breakType;
        }

        public String getBreakName() {
            return breakName;
        }

        public void setBreakName(String breakName) {
            this.breakName = breakName;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }
}
