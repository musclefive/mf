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
public interface FailureDAO {

    @Table("failure")
    @SQL("select top 1000 * from failure order by finishtime desc")
    <T> List<T> queryAllFailureRecord(Converter<T> converter) throws DAOException;

    @Table("failure")
    @SQL("select* from failure where starttime >= ? and starttime <= ? order by starttime desc")
    <T> List<T> queryFailureRecordByDate(String start, String end, Converter<T> converter) throws DAOException;
}
