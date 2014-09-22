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
 * Created by Chao.Cui.VWED on 14-9-16.
 */
@DAO
public interface ProductionDAO {
    @Table("production")
    @SQL("select * from production order by create_time desc")
    <T> List<T> queryAllProduction(Converter<T> converter) throws DAOException;
}
