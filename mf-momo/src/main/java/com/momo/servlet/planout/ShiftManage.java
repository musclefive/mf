package com.momo.servlet.planout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mf.dal.Converter;
import com.mf.dal.DAOFacade;
import com.mf.util.servlet.BaseServlet;
import com.momo.bean.ShiftType;
import com.momo.dao.ShiftManageDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Chao.Cui.VWED on 14-9-16.
 */
public class ShiftManage extends BaseServlet {
    private static final Logger logger = Logger.getLogger(ShiftManage.class);

    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter ShiftManage servlet.");

//        String DT_RowId = getParam(request, "DT_RowId");
//        String userDescript = getParam(request, "userDescript");
//
//        logger.info("ShiftManage DT_RowId:  " + DT_RowId + " userDescript:" + userDescript);


        String method = getParam(request, "method");

        if( method.equals("query")){
            //query method
            queryShiftTypeList(request, response);
        }else if(method.equals("update")){
            //update method
            updateShiftManage(request, response);
        }else if(method.equals("shiftType")){
            //查询所有班次列表 包含所含的工作时间
            queryShiftTypeListWithWorkTime(request,response);
        }else if(method.equals("test")){
            test(request,response);
        }
    }
    public void test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write("success");
    }
    public void queryShiftTypeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter ShiftManage servlet for queryShiftTypeList ");
        try {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();

            List<ShiftType> shiftTypeList = DAOFacade.getDAO(ShiftManageDAO.class).queryShiftTypeList(new Converter<ShiftType>() {
                @Override
                public ShiftType convert(ResultSet resultSet) throws SQLException {
                    ShiftType shiftType = new ShiftType();
                    shiftType.setRow_id(resultSet.getLong("Row_id"));
                    shiftType.setShift_type_id(resultSet.getString("Shift_type_id").trim());
                    shiftType.setShift_type(resultSet.getString("Shift_type").trim());
                    shiftType.setShift_id(resultSet.getString("Shift_id").trim());
                    shiftType.setShift(resultSet.getString("Shift").trim());
                    shiftType.setShift_start_time(resultSet.getTime("Shift_start_time"));
                    shiftType.setShift_start_time(resultSet.getTime("Shift_start_time"));
                    shiftType.setShift_end_time(resultSet.getTime("Shift_end_time"));
                    shiftType.setLunch_start_time(resultSet.getTime("Lunch_start_time"));
                    shiftType.setLunch_end_time(resultSet.getTime("Lunch_end_time"));
                    if (resultSet.getTime("Break_start_time") != null) {
                        shiftType.setBreak_start_time(resultSet.getTime("Break_start_time"));
                    }else{
                        shiftType.setBreak_start_time(Time.valueOf("00:00:00"));
                    }
                    if (resultSet.getTime("Break_end_time") != null) {
                        shiftType.setBreak_end_time(resultSet.getTime("Break_end_time"));
                    }
                    else{
                        shiftType.setBreak_end_time(Time.valueOf("00:00:00"));
                    }
                    shiftType.setEarliest_time(resultSet.getTime("Earliest_time"));
                    shiftType.setLatest_time(resultSet.getTime("Latest_time"));
                    shiftType.setReset_time(resultSet.getTime("Reset_time"));

                    shiftType.setWork_second(resultSet.getString("Work_second").trim());

                    return shiftType;
                }
            });
            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            String ret = objectMapper.writeValueAsString(shiftTypeList);
            ret = "{\"items\":" + ret + "}";
            printWriter.write(ret);

            logger.error("Result : " + ret);

        } catch (Exception e) {
            logger.error("Failed to query all Domain Users.", e);
            throw new ServletException(e);
        }
    }

    public void updateShiftManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter ShiftManage servlet for update user Role permission  ");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();

        String row_id = getParam(request, "row_id");
        String shift_type = getParam(request, "shift_type");
        String shift = getParam(request, "shift");
        String shift_start_time = getParam(request, "shift_start_time");
        String shift_end_time = getParam(request, "shift_end_time");
        String lunch_start_time = getParam(request, "lunch_start_time");
        String lunch_end_time = getParam(request, "lunch_end_time");
        String break_start_time = getParam(request, "break_start_time");
        String break_end_time = getParam(request, "break_end_time");
        String earliest_time = getParam(request, "earliest_time");
        String latest_time = getParam(request, "latest_time");
        String reset_time = getParam(request, "reset_time");
        String work_second;
        long second;
        try {

            Time time_shift_start_time = Time.valueOf(shift_start_time);
            Time time_shift_end_time = Time.valueOf(shift_end_time);
            Time time_lunch_start_time = Time.valueOf(lunch_start_time);
            Time time_lunch_end_time = Time.valueOf(lunch_end_time);
            Time time_break_start_time = Time.valueOf(break_start_time);
            Time time_break_end_time = Time.valueOf(break_end_time);
            Time time_earliest_time = Time.valueOf(earliest_time);
            Time time_latest_time = Time.valueOf(latest_time);
            Time time_reset_time = Time.valueOf(reset_time);
            Long longRow_id = Long.valueOf(row_id);

            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            //默认取得的时间是GMT+8 修改为GMT，以零点为基准
            df.setTimeZone(TimeZone.getTimeZone("GMT"));

            long long_shift_start_time = df.parse(shift_start_time).getTime();
            long long_shift_end_time = df.parse(shift_end_time).getTime();
            logger.info("long_shift_end_time:" + long_shift_end_time);
            long long_lunch_start_time = df.parse(lunch_start_time).getTime();
            long long_lunch_end_time = df.parse(lunch_end_time).getTime();
            long long_break_start_time = df.parse(break_start_time).getTime();
            long long_break_end_time = df.parse(break_end_time).getTime();

            //计划此班次节拍数(上班可用秒数）
//            second = long_shift_end_time + long_lunch_end_time + long_break_end_time -
//                    long_shift_start_time - long_lunch_start_time - long_break_start_time;

            second = getBetween(long_shift_start_time, long_shift_end_time) - getBetween(long_lunch_start_time, long_lunch_end_time) -
                    getBetween(long_break_start_time, long_break_end_time);

            work_second = String.valueOf((second/1000));

            long recordId = DAOFacade.getDAO(ShiftManageDAO.class).updateShiftType(shift_type, shift, time_shift_start_time,
                    time_shift_end_time,time_lunch_start_time ,time_lunch_end_time,
                    time_break_start_time,time_break_end_time,
                    time_earliest_time,time_latest_time,time_reset_time, work_second,longRow_id );
            if(recordId == 0){
                printWriter.write("success");
                logger.info("updateShiftManage Method: successfully update updateShiftManage recordID:" + recordId + " Work_second:" + work_second);

            }else {
                printWriter.write("error");
                logger.info("updateShiftManage Method: error update user updateShiftManage recordID:" + recordId);
            }


        }catch (ParseException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            logger.error("Failed to update user permission", e);
            throw new ServletException(e);
        }
    }

    /*
    * 对于在（00:00:00 - 07:59:59），如01:00:00 (1-14日)计算毫秒数时，需加上12个小时的毫秒数，即需要从前一天的零点为基准(1-13)
    * */
    public static long getBetween(Long timeStart, Long timeEnd){
        Long returnVal = 0l;
        Long standard = 86400000l;

        if(timeEnd < timeStart){
            //结束时间为1点 开始时间为20点
            returnVal = standard  + timeEnd - timeStart;
        }else{
            returnVal = timeEnd - timeStart;
        }

        logger.info("getBetween: " + timeStart + " " + timeEnd + " between:" + returnVal);
        return returnVal;
    }

    /*
     * 查询SQL 语句为：
     * select Shift_type_id, Shift_type, sum(cast(Work_second as bigint)) as work_time from ShiftType group by Shift_type_id, Shift_type
     * order by Shift_type_id
     *
     * 返回json 显示select列表
     * */
    public void queryShiftTypeListWithWorkTime(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter ShiftManage servlet for queryShiftTypeList ");
        try {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();

            List<ShiftType> shiftTypeListWithWorkTime = DAOFacade.getDAO(ShiftManageDAO.class).queryShiftWithWorkTime(new Converter<ShiftType>() {
                @Override
                public ShiftType convert(ResultSet resultSet) throws SQLException {
                    ShiftType shiftType = new ShiftType();
                    shiftType.setShift_type_id(resultSet.getString("Shift_type_id").trim());
                    shiftType.setShift_type(resultSet.getString("Shift_type").trim());
                    shiftType.setWork_time(resultSet.getString("work_time").trim());
                    return shiftType;
                }
            });

            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            String ret = objectMapper.writeValueAsString(shiftTypeListWithWorkTime);
            ret = "{\"items\":" + ret + "}";
            printWriter.write(ret);

            logger.info("Result : " + ret);

        } catch (Exception e) {
            logger.error("Failed to query all Domain Users.", e);
            throw new ServletException(e);
        }
    }

}
