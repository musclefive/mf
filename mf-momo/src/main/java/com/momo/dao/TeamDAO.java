package com.momo.dao;

import com.mf.dal.Converter;
import com.mf.dal.DAOException;
import com.mf.dal.annotation.DAO;
import com.mf.dal.annotation.SQL;
import com.mf.dal.annotation.Table;

import java.util.List;

/**
 * Created by Chao.Cui.VWED on 14-9-16.
 */
@DAO
public interface TeamDAO {
    @Table("Data2014")
    @SQL("select top 100 * from Data2014")
    <T> List<T> queryAllProduction(Converter<T> converter) throws DAOException;

    @Table("Data2014")
    @SQL("select No from Data2014 where No = 1713")
    String queryTest() throws DAOException;

    @Table("Data2014")
    @SQL("select * from Data2014 where catch_time > ? and catch_time < ? and station_id = 'M1650'  order by catch_time")
    <T> List<T> queryDataPerDay(String startDate, String endDate, Converter<T> converter) throws DAOException;

    @Table("teampc9")
    @SQL("select * from teampc9 where catch_time > ? and catch_time < ? and station_id = 'M1650'  order by catch_time")
    <T> List<T> queryTeampcData(String startDate, String endDate, Converter<T> converter) throws DAOException;

    @Table("teampc11")
    @SQL("select * from teampc11 where NO= ?")
    <T> T querySingleData(int number, Converter<T> converter) throws DAOException;

    @Table("teampc11")
    @SQL("select * from teampc11 where catch_time > ? and catch_time < ? and station_id = 'M1650'  order by catch_time")
    <T> List<T> queryDataLive(String startDate, String endDate, Converter<T> converter) throws DAOException;

}
