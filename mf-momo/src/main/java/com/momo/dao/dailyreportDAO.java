package com.momo.dao;
import com.mf.dal.Converter;
import com.mf.dal.DAOException;
import com.mf.dal.annotation.DAO;
import com.mf.dal.annotation.SQL;
import com.mf.dal.annotation.Table;

import java.util.List;

/*每日累计产量查询*/
@DAO
public interface DailyReportDAO {
    @Table("DailyProData")
    @SQL("select * from DailyProData  order by ProDate")
    <T> List<T> queryDailyReport(Converter<T> converter) throws DAOException;
}
