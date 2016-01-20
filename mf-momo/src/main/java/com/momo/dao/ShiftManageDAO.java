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
public interface ShiftManageDAO {
    @Table("ShiftType")
    @SQL("select * from chart.dbo.ShiftType order by Row_id asc")
    <T> List<T> queryShiftTypeList(Converter<T> converter) throws DAOException;


}
