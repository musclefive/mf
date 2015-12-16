package com.momo.servlet.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mf.dal.Converter;
import com.mf.dal.DAOFacade;
import com.mf.util.servlet.BaseServlet;
import com.momo.bean.DomainUser;
import com.momo.bean.JsonTeamPC;
import com.momo.dao.DomainUserDAO;
import com.momo.dao.TeamDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Chao.Cui.VWED on 14-9-16.
 */
public class UserAdmin extends BaseServlet {
    private static final Logger logger = Logger.getLogger(UserAdmin.class);
    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter UserAdmin servlet.");

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();
            List<DomainUser> domainUserList = DAOFacade.getDAO(DomainUserDAO.class).queryDomainUserList(new Converter<DomainUser>() {
                @Override
                public DomainUser convert(ResultSet resultSet) throws SQLException {
                    DomainUser domainUser = new DomainUser();
                    domainUser.setId(resultSet.getLong("id"));
                    domainUser.setDistinguishedName(resultSet.getString("distinguishedName").equals("") ? " ": resultSet.getString("distinguishedName"));
                    domainUser.setsAMAccountName(resultSet.getString("sAMAccountName").equals("") ? " ": resultSet.getString("sAMAccountName"));
                    domainUser.setMail(resultSet.getString("mail").equals("") ? " " : resultSet.getString("mail"));
                    domainUser.setTitle(resultSet.getString("title").equals("") ? " " : resultSet.getString("title"));
                    domainUser.setTelephoneNumber(resultSet.getString("telephoneNumber").equals("") ? " ": resultSet.getString("telephoneNumber"));
                    domainUser.setWhenCreated(resultSet.getString("whenCreated").equals("") ? " ": resultSet.getString("whenCreated"));
                    domainUser.setWhenChanged(resultSet.getString("whenChanged").equals("") ? " ": resultSet.getString("whenChanged"));
                    domainUser.setPhysicalDeliveryOfficeName(resultSet.getString("physicalDeliveryOfficeName").equals("") ? " ": resultSet.getString("physicalDeliveryOfficeName"));
                    domainUser.setName(resultSet.getString("name").equals("") ? " ": resultSet.getString("name"));
                    domainUser.setMailNickname(resultSet.getString("mailNickname").equals("") ? " ": resultSet.getString("mailNickname"));
                    domainUser.setCn(resultSet.getString("cn").equals("") ? " ": resultSet.getString("cn"));
                    domainUser.setUserAccountControl(resultSet.getString("userAccountControl").equals("") ? " ": resultSet.getString("userAccountControl"));
                    domainUser.setDepartment(resultSet.getString("displayName").equals("") ? " ": resultSet.getString("displayName"));
                    domainUser.setUserRole(resultSet.getString("userRole").equals("") ? " ": resultSet.getString("userRole"));
                    domainUser.setUserGroup(resultSet.getString("userGroup").equals("") ? " ": resultSet.getString("userGroup"));
                    domainUser.setObjectGUID(resultSet.getString("objectGUID").equals("") ? " ": resultSet.getString("objectGUID"));

                    return domainUser;
                }
            });

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            String ret = objectMapper.writeValueAsString(domainUserList);
            ret = "{\"items\":" + ret + "}";
            printWriter.write(ret);

            logger.error("Result : " + ret);

        } catch (Exception  e) {
            logger.error("Failed to query all Domain Users.", e);
            throw new ServletException(e);
        }
    }
}
