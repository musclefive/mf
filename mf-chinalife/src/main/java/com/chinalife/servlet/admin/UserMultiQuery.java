package com.chinalife.servlet.admin;

import com.chinalife.dao.UserDAO;
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
import java.util.Calendar;
import java.util.List;

/**
 * Created by Chao.Cui.VWED on 14-5-15.
 */
public class UserMultiQuery extends BaseServlet {
    private static final Logger logger = Logger.getLogger(UserMultiQuery.class);

    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter UserMultiQuery servlet.");

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();

            List<UserSingleQuery.JsonUser> users = DAOFacade.getDAO(UserDAO.class).queryAllUsers(new Converter<UserSingleQuery.JsonUser>() {
                @Override
                public UserSingleQuery.JsonUser convert(ResultSet resultSet) throws SQLException {
                    UserSingleQuery.JsonUser user = new UserSingleQuery.JsonUser();
                    user.setDT_RowId(resultSet.getLong("user_id"));
                    logger.info("User GetDT_RowID:" + user.getDT_RowId());
                    user.setNickname(resultSet.getString("user_nickname"));
                    user.setEmail(resultSet.getString("user_email"));
                    user.setPassword(resultSet.getString("user_pwd"));
                    user.setUser_category(resultSet.getString("user_category"));
                    user.setCreateTime(resultSet.getTimestamp("create_time", Calendar.getInstance()));
                    return user;
                }
            });
            ObjectMapper objectMapper = new ObjectMapper();
            String ret = objectMapper.writeValueAsString(users);
            ret = "{\"items\":" + ret + "}";
            logger.info("Result : " + ret);
            printWriter.write(ret);
        } catch (DAOException e) {
            logger.error("Failed to query all users.", e);
            throw new ServletException(e);
        }
    }
}
