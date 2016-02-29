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

import javax.naming.*;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Chao.Cui.VWED on 14-9-16.
 */
public class UserAdmin extends BaseServlet {
    private static final Logger logger = Logger.getLogger(UserAdmin.class);
    private static final String USER_PERMISSION_ADMIN = "Admin";
    private static final String USER_PERMISSION_USER = "User";
    private static final String USER_PERMISSION_Operator = "Operator";


    private static String adminName = "jvap\\ccserveradmin.vwed";//username@domain
    private static String adminPassword = "Pass@word1";//password
    private static String ldapURL = "LDAP://10.167.8.215:389/";//ip:port
    private static String BaseDN = "OU=Users,OU=VWED,OU=Mail Hosting,OU=Domain Users,DC=jvap,DC=com";
    private static LdapContext ctx = null;
    private static Hashtable env = null;
    private static Control[] connCtls = null;

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
        }else if(method.equals("domain")){
            queryInsertSqlForDomainAccount(request,response);
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
                    } else {
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

    public void queryInsertSqlForDomainAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter queryInsertSqlForDomainAccount servlet for query Domain User by UserName ");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        String returnVal = getUsers();
        logger.error("Result : " + returnVal);

        printWriter.write(returnVal);


    }
    public String getUsers(){
        Hashtable env = new Hashtable();

        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");//"none","simple","strong"
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        env.put(Context.PROVIDER_URL, ldapURL);
        String sn;
        String distinguishedName;
        String sAMAccountName;
        String mail;
        String title;
        String telephoneNumber;
        String whenCreated;
        String whenChanged;
        String proxyAddress;
        String physicalDeliveryOfficeName;
        String name;
        String mailNickname;
        String givenName;
        String cn;
        String company;
        String userAccountControl;
        String msExchUserCulture;
        String department;
        String displayName;
        String description;
        String userPrincipalName;
        String objectGUID;
        String user;

        String sqlList = "";
        try {

            DirContext ctx = new InitialDirContext(env);
            NamingEnumeration list = ctx.list(BaseDN);
            String[] attrIDs = {"sn", "distinguishedName", "sAMAccountName", "mail","title",
                    "telephoneNumber", "whenCreated", "whenChanged", "proxyAddress", "physicalDeliveryOfficeName",
                    "name", "mailNickname", "givenName", "cn", "company", "userAccountControl", "msExchUserCulture",
                    "department", "displayName", "description", "userPrincipalName","objectGUID"};
            String insert = "insert into DomainUser(sn, distinguishedName,sAMAccountName,mail," +
                    "title,telephoneNumber,whenCreated,whenChanged,proxyAddress,physicalDeliveryOfficeName," +
                    "name,mailNickname,givenName,cn,company,userAccountControl,msExchUserCulture," +
                    "department,displayName,description,userPrincipalName,objectGUID) values ";
//            NamingEnumeration bindings = ctx.listBindings("OU=Users,OU=VWED,OU=Mail Hosting,OU=Domain Users,DC=jvap,DC=com");
            String insertValue;
            int count = 0;
            while (list.hasMore()) {
                count  = count + 1;
                NameClassPair nc = (NameClassPair)list.next();
                user = nc.getName() + "," +BaseDN;
                Attributes answer = ctx.getAttributes(user, attrIDs);
                NamingEnumeration ae = answer.getAll();
//                System.out.println(nc.getName());
                Map mapTmp = new HashMap();
                while (ae.hasMore()) {
                    Attribute attr = (Attribute) ae.next();

//                    System.out.print("Attribute:" + attr.getID());
//                    System.out.println(" value:" + attr.get(0));
                    String key = attr.getID();
                    String value = attr.get(0).toString();
                    mapTmp.put(key, value);
                }
                sn = mapTmp.containsKey("sn") ? mapTmp.get("sn").toString() : "";
                distinguishedName = mapTmp.containsKey("distinguishedName") ? mapTmp.get("distinguishedName").toString() : "";
                sAMAccountName = mapTmp.containsKey("sAMAccountName") ? mapTmp.get("sAMAccountName").toString() : "";
                mail = mapTmp.containsKey("mail") ? mapTmp.get("mail").toString() : "";
                title = mapTmp.containsKey("title") ? mapTmp.get("title").toString() : "";
                telephoneNumber = mapTmp.containsKey("telephoneNumber") ? mapTmp.get("telephoneNumber").toString() : "";
                whenCreated = mapTmp.containsKey("whenCreated") ? mapTmp.get("whenCreated").toString() : "";
                whenChanged = mapTmp.containsKey("whenChanged") ? mapTmp.get("whenChanged").toString() : "";
                proxyAddress = mapTmp.containsKey("proxyAddress") ? mapTmp.get("proxyAddress").toString() : "";
                physicalDeliveryOfficeName = mapTmp.containsKey("physicalDeliveryOfficeName") ? mapTmp.get("physicalDeliveryOfficeName").toString() : "";
                name = mapTmp.containsKey("name") ? mapTmp.get("name").toString() : "";
                mailNickname = mapTmp.containsKey("mailNickname") ? mapTmp.get("mailNickname").toString() : "";
                givenName = mapTmp.containsKey("givenName") ? mapTmp.get("givenName").toString() : "";
                cn = mapTmp.containsKey("cn") ? mapTmp.get("cn").toString() : "";
                company = mapTmp.containsKey("company") ? mapTmp.get("company").toString() : "";
                userAccountControl = mapTmp.containsKey("userAccountControl") ? mapTmp.get("userAccountControl").toString() : "";
                msExchUserCulture = mapTmp.containsKey("msExchUserCulture") ? mapTmp.get("msExchUserCulture").toString() : "";
                department = mapTmp.containsKey("department") ? mapTmp.get("department").toString() : "";
                displayName = mapTmp.containsKey("displayName") ? mapTmp.get("displayName").toString() : "";
                description = mapTmp.containsKey("description") ? mapTmp.get("description").toString() : "";
                userPrincipalName = mapTmp.containsKey("userPrincipalName") ? mapTmp.get("userPrincipalName").toString() : "";
                objectGUID = mapTmp.containsKey("objectGUID") ? getGUID(mapTmp.get("objectGUID").toString().getBytes()).toString() : "";

                insertValue = "'" + sn + "'," +
                        "'" + distinguishedName + "'," +
                        "'" + sAMAccountName + "'," +
                        "'" + mail + "'," +
                        "'" + title+ "'," +
                        "'" + telephoneNumber + "'," +
                        "'" + whenCreated + "'," +
                        "'" + whenChanged + "'," +
                        "'" + proxyAddress + "'," +
                        "'" + physicalDeliveryOfficeName + "'," +
                        "'" + name + "'," +
                        "'" + mailNickname + "'," +
                        "'" + givenName + "'," +
                        "'" + cn + "'," +
                        "'" + company + "'," +
                        "'" + userAccountControl + "'," +
                        "'" + msExchUserCulture + "'," +
                        "'" + department + "'," +
                        "'" + displayName + "'," +
                        "'" + description + "'," +
                        "'" + userPrincipalName + "'," +
                        "'" + objectGUID + "'";

//                System.out.println(insert + " ( " + insertValue +" );");
                sqlList = sqlList + insert + " ( " + insertValue +" );</br>";
            }

        } catch (AuthenticationException e) {

            // attempt to reacquire the authentication information

        } catch (NamingException e) {

            // Handle the error

            System.err.println("E:"+ e);

        }
        return sqlList;
//        System.out.println(adminName);
    }

    private static String getGUID(byte[] inArr) {
        StringBuffer guid = new StringBuffer();
        for (int i = 0; i < inArr.length; i++) {
            StringBuffer dblByte = new StringBuffer(
                    Integer.toHexString(inArr[i] & 0xff));
            if (dblByte.length() == 1) {
                guid.append("0");
            }
            guid.append(dblByte);
        }
        return guid.toString();
    }

}
