package com.momo.servlet.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mf.dal.Converter;
import com.mf.dal.DAOFacade;
import com.mf.util.servlet.BaseServlet;
import com.momo.bean.DomainUser;
import com.momo.dao.BreakdownDAO;
import com.momo.dao.DomainUserDAO;
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
 * Created by Chao.Cui.VWED on 14-9-16.
 */
public class UserAdmin extends BaseServlet {
    private static final Logger logger = Logger.getLogger(UserAdmin.class);
    private static final String USER_PERMISSION_ADMIN = "Admin";
    private static final String USER_PERMISSION_USER = "User";
    private static final String USER_PERMISSION_Operator = "Operator";

    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter UserAdmin servlet.");

        String method = getParam(request, "method");
        if( method.equals("query")){
            //query method
            queryDomainUserList(request, response);
        }else if(method.equals("update")){
            //update method
            updateRolePermission(request, response);
        }else if(method.equals("user")){
            String userName = getParam(request, "userName");
            queryDomainUserByName(request, response, userName);
        }
    }

    public void queryDomainUserList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter UserAdmin servlet for query all Domain User List ");
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();
            List<DomainUser> domainUserList = DAOFacade.getDAO(DomainUserDAO.class).queryDomainUserList(new Converter<DomainUser>() {
                @Override
                public DomainUser convert(ResultSet resultSet) throws SQLException {
                    DomainUser domainUser = new DomainUser();
                    domainUser.setId(resultSet.getLong("id"));
                    String distinguishedName = resultSet.getString("distinguishedName").equals("") ? " " : resultSet.getString("distinguishedName");
                    String userPermissionRole;
                    String userPermissionDescript = "";
                    domainUser.setDistinguishedName(distinguishedName);
                    domainUser.setsAMAccountName(resultSet.getString("sAMAccountName").equals("") ? " " : resultSet.getString("sAMAccountName"));
                    domainUser.setMail(resultSet.getString("mail").equals("") ? " " : resultSet.getString("mail"));
                    domainUser.setTitle(resultSet.getString("title").equals("") ? " " : resultSet.getString("title"));
                    domainUser.setTelephoneNumber(resultSet.getString("telephoneNumber").equals("") ? " " : resultSet.getString("telephoneNumber"));
                    domainUser.setWhenCreated(resultSet.getString("whenCreated").equals("") ? " " : resultSet.getString("whenCreated"));
                    domainUser.setWhenChanged(resultSet.getString("whenChanged").equals("") ? " " : resultSet.getString("whenChanged"));
                    domainUser.setPhysicalDeliveryOfficeName(resultSet.getString("physicalDeliveryOfficeName").equals("") ? " " : resultSet.getString("physicalDeliveryOfficeName"));
                    domainUser.setName(resultSet.getString("name").equals("") ? " " : resultSet.getString("name"));
                    domainUser.setMailNickname(resultSet.getString("mailNickname").equals("") ? " " : resultSet.getString("mailNickname"));
                    domainUser.setCn(resultSet.getString("cn").equals("") ? " " : resultSet.getString("cn"));
                    domainUser.setUserAccountControl(resultSet.getString("userAccountControl").equals("") ? " " : resultSet.getString("userAccountControl"));
                    domainUser.setDepartment(resultSet.getString("department").equals("") ? " " : resultSet.getString("department"));
                    domainUser.setObjectGUID(resultSet.getString("objectGUID").equals("") ? " " : resultSet.getString("objectGUID"));

                    userPermissionRole = resultSet.getString("userRole").equals("") ? " " : resultSet.getString("userRole");
                    domainUser.setUserRole(userPermissionRole);
                    domainUser.setUserGroup(resultSet.getString("userGroup").equals("") ? " " : resultSet.getString("userGroup"));
                    //默认级别是user
                    if (resultSet.getString("userDescript") == null || resultSet.getString("userDescript").equals("")) {
                        userPermissionDescript = UserAdmin.USER_PERMISSION_USER;
                    }else{
                        userPermissionDescript = resultSet.getString("userDescript");
                    }
                    domainUser.setUserDescript(userPermissionDescript);
                    return domainUser;
                }
            });

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            String ret = objectMapper.writeValueAsString(domainUserList);
            ret = "{\"items\":" + ret + "}";
            printWriter.write(ret);

            logger.error("Result : " + ret);

        } catch (Exception e) {
            logger.error("Failed to query all Domain Users.", e);
            throw new ServletException(e);
        }
    }

    public void queryDomainUserByName(HttpServletRequest request, HttpServletResponse response, String userName) throws ServletException, IOException {
        logger.info("Enter UserAdmin servlet for query Domain User by UserName ");
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();
            DomainUser domainUser = DAOFacade.getDAO(DomainUserDAO.class).queryDomainUser(userName, new Converter<DomainUser>() {
                @Override
                public DomainUser convert(ResultSet resultSet) throws SQLException {
                    DomainUser domainUser = new DomainUser();
                    domainUser.setId(resultSet.getLong("id"));
                    String distinguishedName = resultSet.getString("distinguishedName").equals("") ? " " : resultSet.getString("distinguishedName");
                    String userPermissionRole;
                    String userPermissionDescript = "";
                    domainUser.setDistinguishedName(distinguishedName);
                    domainUser.setsAMAccountName(resultSet.getString("sAMAccountName").equals("") ? " " : resultSet.getString("sAMAccountName"));
                    domainUser.setMail(resultSet.getString("mail").equals("") ? " " : resultSet.getString("mail"));
                    domainUser.setTitle(resultSet.getString("title").equals("") ? " " : resultSet.getString("title"));
                    domainUser.setTelephoneNumber(resultSet.getString("telephoneNumber").equals("") ? " " : resultSet.getString("telephoneNumber"));
                    domainUser.setWhenCreated(resultSet.getString("whenCreated").equals("") ? " " : resultSet.getString("whenCreated"));
                    domainUser.setWhenChanged(resultSet.getString("whenChanged").equals("") ? " " : resultSet.getString("whenChanged"));
                    domainUser.setPhysicalDeliveryOfficeName(resultSet.getString("physicalDeliveryOfficeName").equals("") ? " " : resultSet.getString("physicalDeliveryOfficeName"));
                    domainUser.setName(resultSet.getString("name").equals("") ? " " : resultSet.getString("name"));
                    domainUser.setMailNickname(resultSet.getString("mailNickname").equals("") ? " " : resultSet.getString("mailNickname"));
                    domainUser.setCn(resultSet.getString("cn").equals("") ? " " : resultSet.getString("cn"));
                    domainUser.setUserAccountControl(resultSet.getString("userAccountControl").equals("") ? " " : resultSet.getString("userAccountControl"));
                    domainUser.setDepartment(resultSet.getString("department").equals("") ? " " : resultSet.getString("department"));
                    domainUser.setObjectGUID(resultSet.getString("objectGUID").equals("") ? " " : resultSet.getString("objectGUID"));

                    userPermissionRole = resultSet.getString("userRole").equals("") ? " " : resultSet.getString("userRole");
                    domainUser.setUserRole(userPermissionRole);
                    domainUser.setUserGroup(resultSet.getString("userGroup").equals("") ? " " : resultSet.getString("userGroup"));
                    //默认级别是user
                    if (resultSet.getString("userDescript") == null || resultSet.getString("userDescript").equals("")) {
                        userPermissionDescript = UserAdmin.USER_PERMISSION_USER;
                    }else{
                        userPermissionDescript = resultSet.getString("userDescript");
                    }
                    domainUser.setUserDescript(userPermissionDescript);
                    return domainUser;
                }
            });

            ObjectMapper objectMapper = new ObjectMapper();
            String ret = objectMapper.writeValueAsString(domainUser);
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
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();
            String distinguishedName = getParam(request, "distinguishedName");
            String userPermission = getParam(request, "userPermission");
            String userDescript = getParam(request, "userDescript");

            logger.info("distinguishedName : " + distinguishedName + " userPermission:" + userPermission + " userDescript:" + userDescript);
            long recordId = DAOFacade.getDAO(DomainUserDAO.class).updateUserPermission(userPermission, userDescript, distinguishedName);
            if(recordId == 0){
                printWriter.write("success");
                logger.info("updateRolePermission Method: successfully update user permission recordID:" + recordId);

            }else {
                printWriter.write("error");
                logger.info("updateRolePermission Method: error update user permission recordID:" + recordId);
            }

        }catch (Exception e) {
            logger.error("Failed to update user permission", e);
            throw new ServletException(e);
        }
    }
}
