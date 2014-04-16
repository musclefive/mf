package com.chinalife.dao;

import com.mf.dal.DAOException;
import com.mf.dal.annotation.DAO;
import com.mf.dal.annotation.SQL;
import com.mf.dal.annotation.Table;

import java.sql.Timestamp;

/**
 * Created by shixin on 3/24/14.
 */
@DAO
public interface HouseSalePictureDAO {
    @Table("house_sale_picture")
    @SQL("insert into house_sale_picture(house_id,picture_name,picture_path,create_time) values (?,?,?,?)")
    void createHouseSalePicture(long id, String name, String path, Timestamp createTime) throws DAOException;
}
