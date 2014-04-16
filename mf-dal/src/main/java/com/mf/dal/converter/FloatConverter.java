package com.mf.dal.converter;


import com.mf.dal.Converter;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ishikin on 14-3-15.
 */
public class FloatConverter implements Converter<Float> {
    @Override
    public Float convert(ResultSet resultSet) throws SQLException {
        return resultSet.getFloat(1);
    }
}
