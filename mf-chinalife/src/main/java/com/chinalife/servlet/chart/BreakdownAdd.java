package com.chinalife.servlet.chart;

import com.chinalife.dao.BreakdownDAO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mf.dal.DAOException;
import com.mf.dal.DAOFacade;
import com.mf.util.servlet.BaseServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

/**
 * Created by Chao.Cui.VWED on 14-9-16.
 */
public class BreakdownAdd extends BaseServlet {
    private static final Logger logger = Logger.getLogger(BreakdownAdd.class);
    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter BreakdownAdd servlet.");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String method = getParam(request, "method");
        if( method.equals("delete")){
            //delete method
            deleteBreakdown(request, response);
        }else if(method.equals("update")){
            //update method
            updateBreakdown(request, response);
        }else{
            //add method
            addBreakdown(request, response);
        }
    }
    public void addBreakdown(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        logger.info("Enter BreakdownAdd servlet for add method.");

        PrintWriter printWriter = response.getWriter();
        try {
            String breakStartTime = getParam(request, "breakStartTime");
            String breakEndTime = getParam(request , "breakEndTime");
            String breakLevel = getParam(request , "breakLevel");
            String breakType = getParam(request , "breakType");
            String breakReason = getParam(request , "breakReason");
            String breakNO = getParam(request , "breakNO");
            String breakDuration = getParam(request , "breakDuration");
            String duration = "";
            String breakName = "";
            //根据年份区别breakdown record 的范围 2014
            String breakScope = getParam(request , "breakScope");
            long start_millionSeconds = 0;
            long end_millionSeconds = 0;
            long long_duration = 0;
            long recordId = 0;
//            long updatedId = 0;
            switch (Integer.valueOf(breakType)){
                case 1 :  breakName = "设备故障";
                    break;
                case 2:   breakName = "物料短缺";
                    break;
                case 3:   breakName = "其他";
                    break;
                default: breakName = "";
                    break;
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                start_millionSeconds = sdf.parse(breakStartTime).getTime();//毫秒
                end_millionSeconds = sdf.parse(breakEndTime).getTime();//毫秒
                long_duration =  end_millionSeconds - start_millionSeconds;

                logger.info("time:" + start_millionSeconds + " " + end_millionSeconds + " " + long_duration);
                logger.info("breakDuration from web page:" + breakDuration);
                duration = String.valueOf(long_duration);

                recordId = DAOFacade.getDAO(BreakdownDAO.class).createBreakdownRecord(breakNO, breakLevel, breakReason, breakType,
                        breakName, breakStartTime, breakEndTime, duration, breakScope);

                //修改teampc中flag 标示， 默认是0，表示没有标记， 1则为有标记需要在页面图表中显示图片
//                String flag = "1";
//                updatedId = DAOFacade.getDAO(TeamDAO.class).updateFlag(flag, Integer.valueOf(breakNO));

                if(recordId !=0){
                    printWriter.write("success");
                }else {
                    printWriter.write("error");
                }
            }
            catch (Exception e){
                e.printStackTrace();
                printWriter.write("error");
            }

            String returnMsg = "{\"items\":success}";
            logger.error("parameters: " + recordId + " " + breakEndTime + " " + breakLevel + " " + breakType + " " + breakReason + " " + returnMsg);

        } catch (Exception  e) {
            logger.error("Failed to add breakdown record.", e);
            printWriter.write("error");
            throw new ServletException(e);
        }
    }

    public void updateBreakdown(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        logger.info("Enter BreakdownAdd servlet for update method.");

        PrintWriter printWriter = response.getWriter();
        try {
            String no = getParam(request, "no");

            String breakStartTime = getParam(request, "breakStartTime");
            String breakEndTime = getParam(request , "breakEndTime");
            String breakLevel = getParam(request , "breakLevel");
            String breakType = getParam(request , "breakType");
            String breakReason = getParam(request , "breakReason");
            String breakNO = getParam(request , "breakNO");
            String breakDuration = getParam(request , "breakDuration");
            String duration = "";
            String breakName = "";
            //根据年份区别breakdown record 的范围 2014
            String breakScope = getParam(request , "breakScope");
            long start_millionSeconds = 0;
            long end_millionSeconds = 0;
            long long_duration = 0;
            long recordId = 0;
//            long updatedId = 0;
            switch (Integer.valueOf(breakType)){
                case 1 :  breakName = "设备故障";
                    break;
                case 2:   breakName = "物料短缺";
                    break;
                case 3:   breakName = "其他";
                    break;
                default: breakName = "";
                    break;
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                start_millionSeconds = sdf.parse(breakStartTime).getTime();//毫秒
                end_millionSeconds = sdf.parse(breakEndTime).getTime();//毫秒
                long_duration =  end_millionSeconds - start_millionSeconds;

                logger.info("time:" + start_millionSeconds + " " + end_millionSeconds + " " + long_duration);
                logger.info("breakDuration from web page:" + breakDuration);
                duration = String.valueOf(long_duration);

                recordId = DAOFacade.getDAO(BreakdownDAO.class).updateBreakdownRecord(breakLevel, breakReason, breakType, breakName, breakStartTime, breakEndTime, duration, Integer.valueOf(no));

                if(recordId == 0){
                    printWriter.write("success");
                }else {
                    printWriter.write("error");
                }
            }
            catch (Exception e){
                e.printStackTrace();
                printWriter.write("error");
            }

        } catch (Exception  e) {
            logger.error("Failed to add breakdown record.", e);
            printWriter.write("error");
            throw new ServletException(e);
        }
    }

    public void deleteBreakdown(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        logger.info("Enter BreakdownAdd servlet for delete method.");

        PrintWriter printWriter = response.getWriter();
        String no = getParam(request, "no");
        long recordId = 0;
        try {
            recordId = DAOFacade.getDAO(BreakdownDAO.class).deleteSingleBreakdown(Integer.valueOf(no));
            logger.error(" delete no : " + no + " recordId:" + recordId);
            if(recordId == 0){
                //如果recordId 为0  表示成功  非0 为不成功
                printWriter.write("success");

            }else {
                printWriter.write("error");
            }
            // to do list 删除成功后 查询当前记录是否存在
        } catch (DAOException e) {
            e.printStackTrace();
        }
     }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class JsonBreakdown {
        private String NO;
        private String breakNO;
        private String breakLevel;
        private String breakReason;
        private String breakType;
        private String breakName;
        private String startTime;
        private String endTime;
        private String duration;
        private String breakScope;


        public String getNO() {
            return NO;
        }

        public void setNO(String NO) {
            this.NO = NO;
        }

        public String getBreakNO() {
            return breakNO;
        }

        public void setBreakNO(String breakNO) {
            this.breakNO = breakNO;
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

        public String getBreakScope() {
            return breakScope;
        }

        public void setBreakScope(String breakScope) {
            this.breakScope = breakScope;
        }
    }
}
