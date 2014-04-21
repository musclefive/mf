package com.chinalife.servlet.admin;

import com.mf.util.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ishikin on 14-4-21.
 */
public class HouseSingleQuery extends BaseServlet {
    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long houseId = getIntParam(request, "house_id");

    }
}
