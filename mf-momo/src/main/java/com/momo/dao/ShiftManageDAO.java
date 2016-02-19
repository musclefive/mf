package com.momo.dao;

import com.mf.dal.Converter;
import com.mf.dal.DAOException;
import com.mf.dal.annotation.DAO;
import com.mf.dal.annotation.SQL;
import com.mf.dal.annotation.SQLControl;
import com.mf.dal.annotation.Table;

import java.sql.Time;
import java.util.List;

/**
 * Created by Chao.Cui.VWED on 2015/12/14.
 */

@DAO
public interface ShiftManageDAO {
    @Table("ShiftType")
    @SQL("select * from ShiftType order by Row_id asc")
    <T> List<T> queryShiftTypeList(Converter<T> converter) throws DAOException;

    @Table("ShiftType")
    @SQLControl(returnAotuGeneratedId = true)
    @SQL("update ShiftType set Shift_type = ?,Shift = ?,Shift_start_time=?,Shift_end_time=?," +
            "Lunch_start_time=?,Lunch_end_time=?,Break_start_time=?,Break_end_time=?, " +
            "Earliest_time=?, Latest_time=?, Reset_time=?, Work_second=? where Row_id = ?")
    Long updateShiftType(String shift_type, String shift, Time shift_start_time, Time shift_end_time,
                         Time lunch_start_time, Time lunch_end_time, Time break_start_time,
                         Time break_end_time, Time Earliest_time, Time Latest_time,
                         Time Reset_time, String work_second,  Long row_id) throws DAOException;

    @Table("ShiftType")
    @SQLControl(returnAotuGeneratedId = true)
    @SQL("delete from ShiftType where Row_id = ?")
    Long deleteShiftType(int userId) throws DAOException;

    @Table("ShiftType")
    @SQL("select Shift_type_id, Shift_type, sum(cast(Work_second as bigint)) as work_time from " +
            " ShiftType group by Shift_type_id, Shift_type order by Shift_type_id")
    <T> List<T> queryShiftWithWorkTime(Converter<T> converter) throws DAOException;

}
