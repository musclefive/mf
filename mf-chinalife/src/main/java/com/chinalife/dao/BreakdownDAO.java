package com.chinalife.dao;
import com.mf.dal.Converter;
import com.mf.dal.DAOException;
import com.mf.dal.annotation.DAO;
import com.mf.dal.annotation.SQL;
import com.mf.dal.annotation.SQLControl;
import com.mf.dal.annotation.Table;

import java.util.List;

@DAO
public interface BreakdownDAO {
    @Table("breakdown")
    @SQL("select * from breakdown")
    <T> List<T> queryAllBreakdown(Converter<T> converter) throws DAOException;

    @Table("breakdown")
    @SQLControl(returnAotuGeneratedId = true)
    @SQL("delete from breakdown where NO = ?")
    Long deleteSingleBreakdown(int NO) throws DAOException;

    @Table("breakdown")
    @SQL("select * from breakdown where NO=?")
    <T> T querySingleBreakdown(int NO, Converter<T> converter) throws DAOException;


    @Table("breakdown")
    @SQL("select * from breakdown where break_scope=?")
    <T> List<T> queryBreakdownPerDay(String break_scope, Converter<T> converter) throws DAOException;

    @Table("breakdown")
    @SQLControl(returnAotuGeneratedId = true)
    @SQL("insert into breakdown(break_no, break_level,break_reason,break_type,break_name,start_time,end_time,duration,break_scope) values (?,?,?,?,?,?,?,?,?)")
    Long createBreakdownRecord(String break_no, String break_level, String break_reason, String break_type, String break_name,
                               String start_time,String end_time, String duration, String break_scope) throws DAOException;

    @Table("breakdown")
    @SQLControl(returnAotuGeneratedId = true)
    @SQL("update breakdown set break_level = ?,break_reason = ?,break_type = ?,break_name = ?,start_time = ?,end_time = ?,duration = ? where no = ?")
    Long updateBreakdownRecord(String break_level, String break_reason, String break_type, String break_name,
                               String start_time,String end_time, String duration ,int NO) throws DAOException;

}
