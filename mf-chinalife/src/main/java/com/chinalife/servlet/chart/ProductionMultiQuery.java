package com.chinalife.servlet.chart;

import com.chinalife.dao.HouseSaleDAO;
import com.chinalife.dao.ProductionDAO;
import com.chinalife.servlet.admin.HouseSingleQuery;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mf.dal.Converter;
import com.mf.dal.DAOException;
import com.mf.dal.DAOFacade;
import com.mf.util.servlet.BaseServlet;
import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Chao.Cui.VWED on 14-9-16.
 */
public class ProductionMultiQuery extends BaseServlet {
    private static final Logger logger = Logger.getLogger(ProductionMultiQuery.class);
    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter ProductionMultiQuery servlet.");

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();

            //pic price area bed baths carport district status title address
            List<ProductionMultiQuery.JsonProduction> productions = DAOFacade.getDAO(ProductionDAO.class).queryAllProduction(new Converter<ProductionMultiQuery.JsonProduction>() {
                @Override
                public ProductionMultiQuery.JsonProduction convert(ResultSet resultSet) throws SQLException {
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                    ProductionMultiQuery.JsonProduction pro = new ProductionMultiQuery.JsonProduction();
                    pro.setHouseId(resultSet.getInt("house_id"));
                    pro.setStation(resultSet.getString("station"));
                    pro.setActual(resultSet.getString("actual"));
                    pro.setPlan(resultSet.getString("plan"));
                    pro.setRoa(resultSet.getString("roa"));
                    pro.setOpr(resultSet.getString("opr"));
//                    pro.setCreateTime(resultSet.getTimestamp("create_time", Calendar.getInstance()));
//                    pro.setCreateTime(resultSet.getTimestamp("create_time", Calendar.getInstance().setTimeInMillis(0);));
                    String str = sdf.format(resultSet.getTimestamp("create_time", Calendar.getInstance()));
                    pro.setCreateTime(str);
                    return pro;
                }
            });
            Calendar c=Calendar.getInstance();
            long milliseconds=c.getTimeInMillis();
            logger.equals("当前时间毫秒值:"+milliseconds);


            ObjectMapper objectMapper = new ObjectMapper();
            String ret = objectMapper.writeValueAsString(productions);

            ret = "{\"items\":" + ret + "}";

            logger.error("Result : " + ret);

            printWriter.write(ret);
        } catch (DAOException e) {
            logger.error("Failed to query all houses.", e);
            throw new ServletException(e);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class JsonProduction {
        private int houseId;
        private String station;
        private String plan;
        private String actual;
        private String roa;
        private String opr;
        private String createTime;

        public int getHouseId() {
            return houseId;
        }

        public void setHouseId(int houseId) {
            this.houseId = houseId;
        }

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
        }

        public String getPlan() {
            return plan;
        }

        public void setPlan(String plan) {
            this.plan = plan;
        }

        public String getActual() {
            return actual;
        }

        public void setActual(String actual) {
            this.actual = actual;
        }

        public String getRoa() {
            return roa;
        }

        public void setRoa(String roa) {
            this.roa = roa;
        }

        public String getOpr() {
            return opr;
        }

        public void setOpr(String opr) {
            this.opr = opr;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
