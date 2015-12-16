package com.momo.dao;

import com.mf.dal.Converter;
import com.mf.dal.DAOException;
import com.mf.dal.annotation.DAO;
import com.mf.dal.annotation.SQL;
import com.mf.dal.annotation.Table;

import java.util.List;

/**
 * Created by Chao.Cui.VWED on 2015/12/14.
 */

@DAO
public interface DomainUserDAO {
    @Table("DomainUser")
    @SQL("select * from DomainUser")
    <T> List<T> queryDomainUserList(Converter<T> converter) throws DAOException;

    @Table("DomainUser")
    @SQL("select * from DomainUser where sAMAccountName = ?")
    <T> T queryDomainUser(Converter<T> converter) throws DAOException;
}
