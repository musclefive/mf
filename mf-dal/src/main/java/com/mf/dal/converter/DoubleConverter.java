package com.mf.dal.converter;


import com.mf.dal.Converter;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ishikin on 14-3-15.
 */
public class DoubleConverter implements Converter<Double> {

    @Override
    public Double convert(ResultSet resultSet) throws SQLException {
        return new Double(resultSet.getDouble(1));
    }
}
