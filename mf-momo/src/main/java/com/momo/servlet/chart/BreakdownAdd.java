package com.momo.servlet.chart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mf.dal.Converter;
import com.momo.bean.JsonBreakdown;
import com.momo.bean.Planning;
import com.momo.dao.BreakdownDAO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mf.dal.DAOException;
import com.mf.dal.DAOFacade;
import com.mf.util.servlet.BaseServlet;
import com.momo.dao.PlanningManage;
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
        }else if(method.equals("query")){
            String startDay = "";
            String endDay = "";
            queryBreakInfo(startDay,endDay,request,response);
        }
        else{
            //add method
            addBreakdown(request, response);
        }
    }

    /*
    * 查询所在时间范围的停台事件
    * String startDay 开始时间
    * String endDay 结束时间
    * */
    public void queryBreakInfo(String startDay, String endDay, HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException{
        logger.info("Enter BreakdownAdd servlet for queryBreakInfo.");

        PrintWriter printWriter = response.getWriter();
        try {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            List<JsonBreakdown> breakInfoList = DAOFacade.getDAO(BreakdownDAO.class).queryAllBreakdown(new Converter<JsonBreakdown>() {
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
                    String break_scope = resultSet.getString("break_scope").trim();

                    //将时间转化成毫秒格式
                    long start_millionSeconds = 0;
                    long end_millionSeconds = 0;

                    String formatStartTime = "";
                    String formatEndTime = "";

                    SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    try {
                        sdf_1.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT = UTC 时间
//                        start_millionSeconds = sdf_1.parse(start_time).getTime();//毫秒
//                        end_millionSeconds = sdf_1.parse(end_time).getTime();//毫秒
                        formatStartTime = String.valueOf(sdf_1.parse(start_time));//毫秒
                        formatEndTime = String.valueOf(sdf_1.parse(end_time));//毫秒
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    record.setNO(NO);
                    record.setBreakNO(break_no);
                    record.setBreakLevel(break_level);
                    record.setBreakReason(break_reason);
                    record.setBreakType(break_type);
                    record.setBreakName(break_name);
//                    record.setStartTime(String.valueOf(start_millionSeconds));
//                    record.setEndTime(String.valueOf(end_millionSeconds));
                    record.setStartTime(formatStartTime);
                    record.setEndTime(formatEndTime);
                    record.setDuration(duration);
                    record.setBreakScope(break_scope);

                    return record;
                }
            });

            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            String ret = objectMapper.writeValueAsString(breakInfoList);
            ret = "{\"items\":" + ret + "}";
            printWriter.write(ret);

            logger.info("Result : " + ret);
        } catch (Exception  e) {
            logger.error("Failed to add breakdown record.", e);
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
}
