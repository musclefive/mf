package com.chinalife.servlet.admin;


import com.chinalife.dao.HouseSaleDAO;
import com.chinalife.dao.HouseSalePictureDAO;
import com.mf.dal.DAOFacade;
import com.mf.util.servlet.BaseServlet;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;

/**
 * Created by shixin on 3/24/14.
 */
public class HouseAddServlet extends BaseServlet {
    private static final Logger logger = Logger.getLogger(HouseAddServlet.class);

    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Enter HouseAddServlet.");
        try {
            /*
            List<FileItem> items = FileUploadUtil.getFileIterms(request, getTmpPath(), -1, -1, null);
            Map<String, String> parameters = FileUploadUtil.getFormFields(items);
            String title = getStr(parameters, "title");
            String district = getStr(parameters, "district");
            String status = getStr(parameters, "status");
            String address = getStr(parameters, "address");
            String type = getStr(parameters, "type");
            int room = getInt(parameters, "room");
            int toilet = getInt(parameters, "toilet");
            int carport = getInt(parameters, "carport");
            double area = getDouble(parameters, "area");
            double minPrice = getDouble(parameters, "min_price");
            double maxPrice = getDouble(parameters, "max_price");
            String desc = getStr(parameters, "description");
            String contactPersion1 = getStr(parameters, "first_contact_name");
            String contactPhone1 = getStr(parameters, "first_contact_phone");
            String contactPersion2 = getStr(parameters, "second_contact_name");
            String contactPhone2 = getStr(parameters, "second_contact_phone");
            */

            String title = getParam(request, "title");
            String district = getParam(request, "district");
            String status = getParam(request, "status");
            String address = getParam(request, "address");
            String type = getParam(request, "type");
            int room = getIntParam(request, "room");
            int toilet = getIntParam(request, "toilet");
            int carport = getIntParam(request, "carport");
            double area = getDoubleParam(request, "area");
            double minPrice = getDoubleParam(request, "min_price");
            double maxPrice = getDoubleParam(request, "max_price");
            String desc = getParam(request, "description");
            //处理textarea中的换行符
            if (desc != null) {
                if(desc.length() != 0){
                    desc = desc.replaceAll("\n", "<br />");
                    desc = desc.replaceAll("\r", "<br />");
                    desc = desc.replaceAll("\"", "'");
                }
            }
            String contactPersion1 = getParam(request, "first_contact_name");
            String contactPhone1 = getParam(request, "first_contact_phone");
            String contactPersion2 = getParam(request, "second_contact_name");
            String contactPhone2 = getParam(request, "second_contact_phone");

            String savedFilesStr = getParam(request, "saved_files");
            String[] savedFiles = savedFilesStr.split(";");
            Validate.notEmpty(savedFiles, "Must pass saved files path.");

            Long houseSaleId = DAOFacade.getDAO(HouseSaleDAO.class)
                    .createHouseSale(title, district, status, address, type, room, toilet, carport, area, minPrice, maxPrice, desc.trim(),
                            contactPersion1, contactPhone1, contactPersion2, contactPhone2, new Timestamp(System.currentTimeMillis()));

            for (String savedFile : savedFiles) {
                File file = new File(savedFile);
                Validate.isTrue(file.exists() && file.isFile(), "Can not find file " + savedFile);

                DAOFacade.getDAO(HouseSalePictureDAO.class).createHouseSalePicture(
                        houseSaleId, file.getName(), file.getPath(), new Timestamp(System.currentTimeMillis()));
            }

            getSuccessDispatcher(request).forward(request, response);
        } catch (Exception e) {
            logger.error(e);
            throw new ServletException(e);
        }
    }
}
