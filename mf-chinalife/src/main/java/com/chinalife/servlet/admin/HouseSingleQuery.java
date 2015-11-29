package com.chinalife.servlet.admin;

import com.chinalife.dao.HouseSaleDAO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mf.dal.Converter;
import com.mf.dal.DAOException;
import com.mf.dal.DAOFacade;
import com.mf.util.servlet.BaseServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by ishikin on 14-4-21.
 */
public class HouseSingleQuery extends BaseServlet {
    private static final Logger logger = Logger.getLogger(HouseSingleQuery.class);

    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int houseId = getIntParam(request, "house_id");
        logger.info("this is house Single Query Servlet house_id:"+ houseId);
        try {
            SaledHouse house = DAOFacade.getDAO(HouseSaleDAO.class).queryHouse(houseId, new Converter<SaledHouse>() {
                @Override
                public SaledHouse convert(ResultSet resultSet) throws SQLException {
                    SaledHouse saledHouse = new SaledHouse();
                    saledHouse.setHouseId(resultSet.getInt("house_id"));
                    saledHouse.setTitle(resultSet.getString("title"));
                    saledHouse.setDistrict(resultSet.getString("district"));
                    saledHouse.setStatus(resultSet.getString("status"));
                    saledHouse.setAddress(resultSet.getString("address"));
                    saledHouse.setType(resultSet.getString("type"));
                    saledHouse.setRoom(resultSet.getInt("room"));
                    saledHouse.setToilet(resultSet.getInt("toilet"));
                    saledHouse.setCarport(resultSet.getInt("carport"));
                    saledHouse.setArea(resultSet.getDouble("area"));
                    saledHouse.setMinPrice(resultSet.getDouble("min_price"));
                    saledHouse.setMaxPrice(resultSet.getDouble("max_price"));
                    saledHouse.setDescription(resultSet.getString("description").trim());
                    saledHouse.setContactPerson1(resultSet.getString("contact_person1"));
                    saledHouse.setContactPhone1(resultSet.getString("contact_phone1"));
                    saledHouse.setContactPerson2(resultSet.getString("contact_person2"));
                    saledHouse.setContactPhone2(resultSet.getString("contact_phone2"));
                    saledHouse.setCreateTime(resultSet.getTimestamp("create_time"));
                    saledHouse.setUpdateTime(resultSet.getTimestamp("update_time"));
                    return saledHouse;
                }
            });
            logger.info("house description:" + house.getDescription());
            addAttribute(request, Scope.REQUEST, "house", house);
            getSuccessDispatcher(request).forward(request, response);
        } catch (DAOException e) {
            logger.error("Faild to query house for id : " + houseId, e);
            throw new ServletException(e);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class SaledHouse {
        private int houseId;
        private String title;
        private String district;
        private String status;
        private String address;
        private String type;
        private int room;
        private int toilet;
        private int carport;
        private double area;
        private double minPrice;
        private double maxPrice;
        private String description;
        private String contactPerson1;
        private String contactPhone1;
        private String contactPerson2;
        private String contactPhone2;
        private Timestamp createTime;
        private Timestamp updateTime;

        public int getHouseId() {
            return houseId;
        }

        public String getTitle() {
            return title;
        }

        public String getDistrict() {
            return district;
        }

        public String getStatus() {
            return status;
        }

        public String getAddress() {
            return address;
        }

        public String getType() {
            return type;
        }

        public int getRoom() {
            return room;
        }

        public int getToilet() {
            return toilet;
        }

        public int getCarport() {
            return carport;
        }

        public double getArea() {
            return area;
        }

        public double getMinPrice() {
            return minPrice;
        }

        public double getMaxPrice() {
            return maxPrice;
        }

        public String getDescription() {
            return description;
        }

        public String getContactPerson1() {
            return contactPerson1;
        }

        public String getContactPhone1() {
            return contactPhone1;
        }

        public String getContactPerson2() {
            return contactPerson2;
        }

        public String getContactPhone2() {
            return contactPhone2;
        }

        public Timestamp getCreateTime() {
            return createTime;
        }

        public Timestamp getUpdateTime() {
            return updateTime;
        }

        public void setHouseId(int houseId) {
            this.houseId = houseId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setRoom(int room) {
            this.room = room;
        }

        public void setToilet(int toilet) {
            this.toilet = toilet;
        }

        public void setCarport(int carport) {
            this.carport = carport;
        }

        public void setArea(double area) {
            this.area = area;
        }

        public void setMinPrice(double minPrice) {
            this.minPrice = minPrice;
        }

        public void setMaxPrice(double maxPrice) {
            this.maxPrice = maxPrice;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setContactPerson1(String contactPerson1) {
            this.contactPerson1 = contactPerson1;
        }

        public void setContactPhone1(String contactPhone1) {
            this.contactPhone1 = contactPhone1;
        }

        public void setContactPerson2(String contactPerson2) {
            this.contactPerson2 = contactPerson2;
        }

        public void setContactPhone2(String contactPhone2) {
            this.contactPhone2 = contactPhone2;
        }

        public void setCreateTime(Timestamp createTime) {
            this.createTime = createTime;
        }

        public void setUpdateTime(Timestamp updateTime) {
            this.updateTime = updateTime;
        }
    }
}
