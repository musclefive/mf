package com.chinalife.servlet;


import com.chinalife.dao.UserDAO;
import com.chinalife.user.UserCategory;
import com.mf.dal.DAOException;
import com.mf.dal.DAOFacade;
import com.mf.dal.DBAccesser;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Created by shixin on 3/18/14.
 */
public class TestRegisterServlet {
/*    private static String log4jFileUbuntu = "/home/shixin/workspace/chinalife/src/test/resources/log4j.properties";
    private static String dbFileUbuntu = "/home/shixin/workspace/chinalife/src/test/resources/db_config.xml";*/

    private static String dbFileUbuntu = "E:\\develop\\mf\\mf-chinalife\\src\\test\\resources\\db_config.xml";
    private static String log4jFileUbuntu= "E:\\develop\\mf\\mf-chinalife\\src\\test\\resources\\log4j.properties";
    @Before
    public void init() throws FileNotFoundException {
        PropertyConfigurator.configure(log4jFileUbuntu);

        File confFile = new File(dbFileUbuntu);
        Validate.isTrue(confFile.exists());

        Properties properties = new Properties();
        properties.put("max.total", 50);

        DBAccesser.createInstance(new FileInputStream(confFile), properties);
    }

    @Test
    public void testDBFirst() throws DAOException {
        UserDAO userDAO = DAOFacade.getDAO(UserDAO.class);
        Validate.notNull(userDAO);

        String nickname = "shixincc";
        Long userId = userDAO.queryUserByNickname(nickname);
        System.out.println(userId);

        String email = "shixincc@xiaomi.com";
        userId = userDAO.queryUserByEmail(email);
        System.out.println(userId);

        String pwd = "123";
        userId = userDAO.createUser(nickname, "123", email, UserCategory.CUSTOMER.toString(), new Timestamp(System.currentTimeMillis()));
        System.out.println(userId);
    }
}
