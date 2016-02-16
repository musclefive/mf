package com.momo.servlet.planout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mf.dal.Converter;
import com.mf.dal.DAOFacade;
import com.mf.dal.DBAccesser;
import com.mf.util.servlet.BaseServlet;
import com.momo.bean.Planning;
import com.momo.bean.ShiftType;
import com.momo.dao.PlanningManage;
import com.momo.dao.ShiftManageDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Chao.Cui.VWED on 14-9-16.
 */
public class PlanManage extends BaseServlet {
    private static final Logger logger = Logger.getLogger(PlanManage.class);

    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter PlanManage servlet.");

//        String DT_RowId = getParam(request, "DT_RowId");
//        String userDescript = getParam(request, "userDescript");
//
//        logger.info("ShiftManage DT_RowId:  " + DT_RowId + " userDescript:" + userDescript);


        String method = getParam(request, "method");

        if( method.equals("query")){
            //query method today format 2016-01-22
            String startDay = getParam(request, "startDay");
            String type = getParam(request, "type");
            //查询计划的生产信息 或者已计划的生产信息
            if(type.equals("planning")){
                //startDay 是今天日期
                queryPlanningManageList(startDay, request, response);

            }else if(type.equals("planned")){
                String endDay = getParam(request, "endDay");

                queryPlannedManageList(startDay,endDay, request, response);
            }else{

            }
        }else if(method.equals("update")){
            //update method
            updatePlanningOut(request, response);
        }
    }

    /*
    * 查询当天日期之后的生产计划历史信息
    * */
    public void queryPlanningManageList(String today, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter PlanManage servlet for queryPlannedManageList ");

        int twoWeekDays = 10;
        int count = 0;
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        try {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();

            List<Planning> planningList = DAOFacade.getDAO(PlanningManage.class).queryPlanningListAfterToday(today, new Converter<Planning>() {
                @Override
                public Planning convert(ResultSet resultSet) throws SQLException {
                    Planning planning = new Planning();
                    planning.setP_Row_id(resultSet.getLong("P_Row_id"));
                    planning.setPlanning_date(resultSet.getDate("Planning_date"));
                    planning.setPlanning_qty(resultSet.getInt("Planning_qty"));
                    planning.setCycle_time(resultSet.getFloat("Cycle_time"));
                    if (resultSet.getString("P_Shift_type_id") != null) {
                        planning.setP_Shift_type_id(resultSet.getString("P_Shift_type_id").trim());
                    } else {
                        planning.setP_Shift_type_id("0");
                    }
                    if (resultSet.getString("P_Shift_type") != null) {
                        planning.setP_Shift_type(resultSet.getString("P_Shift_type").trim());
                    } else {
                        planning.setP_Shift_type(" ");
                    }
                    if (resultSet.getString("cw") != null) {
                        planning.setCw(resultSet.getString("cw").trim());
                    } else {
                        planning.setCw(" ");
                    }
                    return planning;
                }
            });
            //记录当前生产计划表中已有的数据量
            count = planningList.size();
            /*
            *如果当天日期01-28 count=4 则数据库中已存在的日期01-28 01-29 01-30 01-31 4条数据 则设置righNow时间为
            * 02-01
            */
            rightNow.add(Calendar.DAY_OF_MONTH, count - 1);

            //自动补充数据库记录中没有的数据
            for(int k = 0; k <= twoWeekDays - count; k++){
                Long recordId;
                rightNow.add(Calendar.DAY_OF_MONTH, 1);
                String plannedDate = sim.format(rightNow.getTime());
                System.out.println("insert days:" + plannedDate);
                recordId = DAOFacade.getDAO(PlanningManage.class).insertPlanning(plannedDate);
                if(recordId == 0){
                    break;
                } else {
                    logger.info("queryPlanningManageList Inserted RecordID:" + recordId + "planning date:" + plannedDate);
                    Planning planning = new Planning();
                    planning.setP_Row_id(recordId);
                    planning.setPlanning_date(java.sql.Date.valueOf(plannedDate));
                    planning.setPlanning_qty(0);
                    planning.setCycle_time(Float.valueOf("0"));
                    planning.setP_Shift_type_id("0");
                    planning.setP_Shift_type(" ");
                    planning.setCw(" ");
                    planningList.add(planning);
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            String ret = objectMapper.writeValueAsString(planningList);
            ret = "{\"items\":" + ret + "}";
            printWriter.write(ret);

            logger.info("Result : " + ret);

        } catch (Exception e) {
            logger.error("Failed to query all Domain Users.", e);
            throw new ServletException(e);
        }
    }

    /*
    * 查询开始时间到结束时间内的生产计划历史信息
    * */
    public void queryPlannedManageList(String startDay, String endDay, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter PlanManage servlet for queryPlannedManageList startDay:" + startDay  + " endDay:" + endDay) ;
        try {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();

            List<Planning> planningList = DAOFacade.getDAO(PlanningManage.class).queryPlanningListBeforeToday(startDay, endDay, new Converter<Planning>() {
                @Override
                public Planning convert(ResultSet resultSet) throws SQLException {
                    Planning planning = new Planning();
                    planning.setP_Row_id(resultSet.getLong("P_Row_id"));
                    planning.setPlanning_date(resultSet.getDate("Planning_date"));
                    planning.setPlanning_qty(resultSet.getInt("Planning_qty"));
                    planning.setCycle_time(resultSet.getFloat("Cycle_time"));
                    if (resultSet.getString("P_Shift_type_id") != null) {
                        planning.setP_Shift_type_id(resultSet.getString("P_Shift_type_id").trim());
                    } else {
                        planning.setP_Shift_type_id("0");
                    }
                    if (resultSet.getString("P_Shift_type") != null) {
                        planning.setP_Shift_type(resultSet.getString("P_Shift_type").trim());
                    } else {
                        planning.setP_Shift_type(" ");
                    }
                    if (resultSet.getString("cw") != null) {
                        planning.setCw(resultSet.getString("cw").trim());
                    } else {
                        planning.setCw(" ");
                    }
                    return planning;
                }
            });

            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            String ret = objectMapper.writeValueAsString(planningList);
            ret = "{\"items\":" + ret + "}";
            printWriter.write(ret);

            logger.info("Result : " + ret);

        } catch (Exception e) {
            logger.error("Failed to query all Domain Users.", e);
            throw new ServletException(e);
        }
    }

    public void updatePlanningOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter PlanManage servlet for updatePlanningout  ");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();

        String planning_date = getParam(request, "planning_date");
        String p_Row_id = getParam(request, "p_Row_id");
        String planout = getParam(request, "planout");
        String cycleTime = getParam(request, "cycleTime");
        String shift_type_id = getParam(request, "shift_type_id");
        String shift_type = getParam(request, "shift_type");

        java.sql.Date planing = java.sql.Date.valueOf(planning_date);
        boolean flag= true;
        int intPlanout;
        Float fCycleTime;
        Long row_id;
        try{
            intPlanout = Integer.valueOf(planout);
            fCycleTime = (float)(Math.round(Float.valueOf(cycleTime)*100))/100;
            row_id = Long.valueOf(p_Row_id);

            long recordId = DAOFacade.getDAO(PlanningManage.class).updatePlanning(planing, intPlanout,
                    shift_type_id, shift_type, fCycleTime, row_id);
            if(recordId == 0){
                printWriter.write("success");
                logger.info("updatePlanningOut Method: successfully update updatePlanningOut recordID:" + recordId + " fCycleTime:" + fCycleTime);

            }else {
                printWriter.write("error");
                logger.error("updatePlanningOut Method: error update user updatePlanningOut recordID:" + recordId);
            }


        }catch (NumberFormatException e) {
            logger.error("Failed to update updatePlanningOut", e);
            printWriter.write("error");
        }
        catch (Exception e) {
            logger.error("Failed to update updatePlanningOut", e);
            throw new ServletException(e);
        }
    }

}
