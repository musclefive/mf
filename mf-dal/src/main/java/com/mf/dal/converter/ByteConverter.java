package com.mf.dal.converter;


import com.mf.dal.Converter;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ishikin on 14-3-15.
 */
public class ByteConverter implements Converter<Byte> {
    @Override
    public Byte convert(ResultSet resultSet) throws SQLException {
        return new Byte(resultSet.getByte(1));
    }
}
