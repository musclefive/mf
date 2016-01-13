package com.momo.dao;

import com.mf.dal.Converter;
import com.mf.dal.DAOException;
import com.mf.dal.annotation.DAO;
import com.mf.dal.annotation.SQL;
import com.mf.dal.annotation.SQLControl;
import com.mf.dal.annotation.Table;

import java.util.List;

/**
 * Created by Chao.Cui.VWED on 2016/1/7.
 */
@DAO
public interface UserPermissionDAO {

    @Table("UserPermission")
    @SQL("select * from UserPermission")
    <T> List<T> queryUserPermissionList(Converter<T> converter) throws DAOException;

    @Table("UserPermission")
    @SQL("select * from UserPermission where distinguishedName = ?")
    <T> T queryUserPermission(String distinguishedName, Converter<T> converter) throws DAOException;

    @Table("UserPermission")
    @SQLControl(returnAotuGeneratedId = true)
    @SQL("insert into UserPermission(distinguishedName, permissionRole,permissionDescript) values (?,?,?)")
    Long createPermissionRecord(String distinguishedName, String permissionRole, String permissionDescript);

    @Table("UserPermission")
    @SQLControl(returnAotuGeneratedId = true)
    @SQL("delete from UserPermission distinguishedName NO = ?")
    Long deletePermissionRecord(String distinguishedName) throws DAOException;
}
