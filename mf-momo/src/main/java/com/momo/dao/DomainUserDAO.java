package com.momo.dao;

import com.mf.dal.Converter;
import com.mf.dal.DAOException;
import com.mf.dal.annotation.DAO;
import com.mf.dal.annotation.SQL;
import com.mf.dal.annotation.SQLControl;
import com.mf.dal.annotation.Table;

import java.util.List;

/**
 * Created by Chao.Cui.VWED on 2015/12/14.
 */

@DAO
public interface DomainUserDAO {
    @Table("DomainUser")
    @SQL("select * from DomainUser  order by userRole desc")
    <T> List<T> queryDomainUserList(Converter<T> converter) throws DAOException;

    @Table("DomainUser")
    @SQL("select * from DomainUser where sAMAccountName = ?")
    <T> T queryDomainUser(String sAMAccountName, Converter<T> converter) throws DAOException;

    @Table("DomainUser")
    @SQL("select * from DomainUser left join UserPermission on DomainUser.distinguishedName = UserPermission.distinguishedName order by permissionRole desc")
    <T> List<T> queryDomainUserListwithPermission(Converter<T> converter) throws DAOException;

    @Table("DomainUser")
    @SQLControl(returnAotuGeneratedId = true)
    @SQL("update DomainUser set userRole = ?,userDescript = ? where distinguishedName = ?")
    Long updateUserPermission(String userRole, String userDescript, String distinguishedName) throws DAOException;
}
