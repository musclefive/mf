package com.momo.dao;

import com.mf.dal.Converter;
import com.mf.dal.DAOException;
import com.mf.dal.annotation.DAO;
import com.mf.dal.annotation.SQL;
import com.mf.dal.annotation.SQLControl;
import com.mf.dal.annotation.Table;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Created by Chao.Cui.VWED on 2015/12/14.
 */

@DAO
public interface PlanningManage {
    @Table("Planning")
    @SQL("select * from Planning order by Planning_date desc")
    <T> List<T> queryAllPlanning(Converter<T> converter) throws DAOException;


    @Table("Planning")
    @SQL("select * from Planning where  Convert(varchar(100),Planning_date,23) >= ? order by Planning_date")
    <T> List<T> queryPlanningListAfterToday(String today, Converter<T> converter) throws DAOException;


    @Table("Planning")
    @SQL("select * from Planning where  Convert(varchar(100),Planning_date,23) < ? order by Planning_date desc")
    <T> List<T> queryPlanningListBeforeToday(String today, Converter<T> converter) throws DAOException;

    @Table("Planning")
    @SQLControl(returnAotuGeneratedId = true)
    @SQL("update Planning set Planning_date = ?,Planning_qty = ?,P_Shift_type_id=?,P_Shift_type=?," +
            "Cycle_time=?  where P_Row_id = ?")
    Long updatePlanning(Date Planning_date, int Planning_qty, String P_Shift_type_id, String P_Shift_type,
                         Float Cycle_time, Long P_Row_id) throws DAOException;

    @Table("Planning")
    @SQLControl(returnAotuGeneratedId = true)
    @SQL("insert into Planning (Planning_date) values (Convert(varchar(100),?,23))")
    Long insertPlanning(String planningDate) throws DAOException;
}
