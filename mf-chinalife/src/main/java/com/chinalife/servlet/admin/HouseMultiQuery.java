package com.chinalife.servlet.admin;

import com.chinalife.dao.HouseSaleDAO;
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
import java.util.List;

/**
 * Created by ishikin on 14-4-21.
 */
public class HouseMultiQuery extends BaseServlet {
    private static final Logger logger = Logger.getLogger(HouseMultiQuery.class);

    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter HouseMultiQuery servlet.");

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();

            List<HouseSingleQuery.SaledHouse> houses = DAOFacade.getDAO(HouseSaleDAO.class).queryAllHouse(new Converter<HouseSingleQuery.SaledHouse>() {
                @Override
                public HouseSingleQuery.SaledHouse convert(ResultSet resultSet) throws SQLException {
                    HouseSingleQuery.SaledHouse house = new HouseSingleQuery.SaledHouse();
                    house.setHouseId(resultSet.getInt("house_id"));
                    house.setTitle(resultSet.getString("title"));
                    house.setAddress(resultSet.getString("address"));
                    house.setContactPhone1("contact_phone1");
                    return house;
                }
            });

            ObjectMapper objectMapper = new ObjectMapper();
            String ret = objectMapper.writeValueAsString(houses);

            logger.error("Result : " + ret);

            printWriter.write(ret);
        } catch (DAOException e) {
            logger.error("Failed to query all houses.", e);
            throw new ServletException(e);
        }
    }
}
