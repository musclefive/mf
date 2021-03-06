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
 * Created by shixin on 3/24/14.
 */
@DAO
public interface HouseSaleDAO {
    @Table("house_sale")
    @SQLControl(returnAotuGeneratedId = true)
    @SQL("insert into house_sale(title,district,status,address,type,room,toilet,carport,area,min_price,max_price," +
            "description,contact_person1,contact_phone1,contact_person2,contact_phone2,create_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
    Long createHouseSale(String title, String district, String status, String address, String type,
                         int room, int toilet, int carport, double area, double min_price, double max_price,
                         String desc, String person1, String phone1, String person2, String phone2, Timestamp createTime) throws DAOException;

    @Table("house_sale")
    @SQL("select * from house_sale where house_id=?")
    <T> T queryHouse(int houseId, Converter<T> converter) throws DAOException;

    @Table("house_sale")
    @SQL("select house_id,title,address,max_price,district,create_time from house_sale order by create_time desc")
    <T> List<T> queryHouseForItems(Converter<T> converter) throws DAOException;

    @Table("house_sale")
    @SQL("select * from house_sale order by create_time desc")
    <T> List<T> queryAllHouse(Converter<T> converter) throws DAOException;
}
