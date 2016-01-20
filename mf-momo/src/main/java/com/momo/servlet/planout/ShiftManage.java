package com.momo.servlet.planout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mf.dal.Converter;
import com.mf.dal.DAOFacade;
import com.mf.util.servlet.BaseServlet;
import com.momo.bean.DomainUser;
import com.momo.bean.ShiftType;
import com.momo.dao.DomainUserDAO;
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
import java.util.List;

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

        queryShiftTypeList(request, response);

//        String method = getParam(request, "method");
//
//
//
//        if( method.equals("query")){
//            //query method
//            queryDomainUserList(request, response);
//        }else if(method.equals("update")){
//            //update method
//            updateRolePermission(request, response);
//        }
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

    public void updateRolePermission(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter UserAdmin servlet for update user Role permission  ");
        try {


        }catch (Exception e) {
            logger.error("Failed to update user permission", e);
            throw new ServletException(e);
        }
    }
}
