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
            String today = getParam(request, "today");
            String type = getParam(request, "type");
            //查询计划的生产信息 或者已计划的生产信息
            if(type.equals("planning")){
                queryPlanningManageList(today, request, response);

            }else if(type.equals("planned")){
                queryPlannedManageList(today, request, response);
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
    * 查询当天日期之前的生产计划历史信息
    * */
    public void queryPlannedManageList(String today, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter PlanManage servlet for queryPlannedManageList ");
        try {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();

            List<Planning> planningList = DAOFacade.getDAO(PlanningManage.class).queryPlanningListBeforeToday(today, new Converter<Planning>() {
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
            fCycleTime = Float.valueOf(cycleTime);
            row_id = Long.valueOf(p_Row_id);

            long recordId = DAOFacade.getDAO(PlanningManage.class).updatePlanning(planing, intPlanout,
                    shift_type_id, shift_type, fCycleTime, row_id);
            if(recordId == 0){
                printWriter.write("success");
                logger.info("updatePlanningOut Method: successfully update updatePlanningOut recordID:" + recordId);

            }else {
                printWriter.write("error");
                logger.error("updatePlanningOut Method: error update user updatePlanningOut recordID:" + recordId);
            }


        }
        catch (Exception e) {
            logger.error("Failed to update updatePlanningOut", e);
            throw new ServletException(e);
        }
    }

}
