package com.chinalife.servlet.admin;

import com.chinalife.dao.UserDAO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mf.dal.DAOException;
import com.mf.dal.DAOFacade;
import com.mf.util.servlet.BaseServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

/**
 * Created by Chao.Cui.VWED on 14-5-15.
 */
public class UserSingleQuery extends BaseServlet {
    private static final Logger logger = Logger.getLogger(UserSingleQuery.class);

    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter UserSingleQuery servlet.");
        int userId = getIntParam(request, "user_id");
        String type = getParam(request, "type");
        logger.info(" UserSingleQuery the method type is :" + type);
        if("delete".equals(type)){
            deleteSelectedUser(request, response);
        }
    }

    protected void deleteSelectedUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int userId = getIntParam(request, "user_id");
        userId = 100;
        logger.info("the deleted user_id is :" + userId);
        try {
            response.setContentType("text/html");
//            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();

            /*删除某一个用户，为啥返回的num为0， */
            Long num = DAOFacade.getDAO(UserDAO.class).deleteUser(userId);
            if (num >= 0){
                logger.info("sucessfully delete the user, return num is:" + num);
                printWriter.println("success");
                String json = "cuichao";
//                String json = "{\"items\":[{\"houseId\":10,\"title\":\"ffffadsfaf\"}]}";
                logger.info("json:" + json);
                printWriter.write(json);
            }
        }catch (DAOException e) {
                logger.error("Failed to query all houses.", e);
                throw new ServletException(e);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class JsonUser {

        @JsonProperty(value = "DT_RowId")
        private Long dT_RowId;

        @JsonProperty(value = "nickname")
        private String nickname;

        @JsonProperty(value = "email")
        private String email;

        @JsonProperty(value = "password")
        private String password;

        @JsonProperty(value = "user_category")
        private String user_category;

        @JsonProperty(value = "createTime")
        private Timestamp createTime;

        @JsonProperty(value = "updateTime")
        private Timestamp updateTime;


        public Long getdT_RowId() {
            return dT_RowId;
        }

        public void setdT_RowId(Long dT_RowId) {
            this.dT_RowId = dT_RowId;
        }

        public String getNickname() {
            return nickname;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getUser_category() {
            return user_category;
        }

        public Timestamp getCreateTime() {
            return createTime;
        }

        public Timestamp getUpdateTime() {
            return updateTime;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setUser_category(String user_category) {
            this.user_category = user_category;
        }

        public void setCreateTime(Timestamp createTime) {
            this.createTime = createTime;
        }

        public void setUpdateTime(Timestamp updateTime) {
            this.updateTime = updateTime;
        }
    }
}
