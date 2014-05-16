package com.chinalife.dao;


import com.mf.dal.Converter;
import com.mf.dal.DAOException;
import com.mf.dal.annotation.DAO;
import com.mf.dal.annotation.SQL;
import com.mf.dal.annotation.SQLControl;
import com.mf.dal.annotation.Table;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by shixin on 3/18/14.
 */
@DAO
public interface UserDAO {
    @Table("user")
    @SQL("select user_id from user where user_nickname=?")
    Long queryUserByNickname(String nickname) throws DAOException;

    @Table("user")
    @SQL("select user_id from user where user_email=?")
    Long queryUserByEmail(String email) throws DAOException;

    @Table("user")
    @SQLControl(returnAotuGeneratedId = true)
    @SQL("insert into user(user_nickname,user_pwd,user_email,user_category,create_time) values (?,?,?,?,?)")
    Long createUser(String nickname, String pwd, String email, String category, Timestamp createTime) throws DAOException;

    @Table("user")
    @SQL("select user_id from user where user_nickname=? and user_pwd=?")
    Long login(String nickname, String pwd) throws DAOException;

    @Table("user")
    @SQL("select * from user order by create_time desc")
    <T> List<T> queryAllUsers(Converter<T> converter) throws DAOException;

    @Table("user")
    @SQLControl(returnAotuGeneratedId = true)
    @SQL("delete from user where user_id = ?")
    Long deleteUser(int userId) throws DAOException;
}
