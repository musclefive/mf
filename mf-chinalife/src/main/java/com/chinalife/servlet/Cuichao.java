package com.chinalife.servlet;

/**
 * Created by Chao.Cui.VWED on 14-5-7.
 */
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mf.util.io.FileIOUtil;
import com.mf.util.servlet.BaseServlet;
import com.mf.util.servlet.FileUploadUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Cuichao  extends BaseServlet {
    private static Logger logger = Logger.getLogger(UploadServlet.class);

    private static final String SAVED_PATH_KEY = "save.path";
    private static final String PARAM_KEY_GET_FILE = "getFile";
    private static final String PARAM_KEY_GET_THUMBNAIL = "getThumbnail";
    private static final String PARAM_KEY_DEL_FILE = "delFile";

    private String currentDay;

    @Override
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Enter Cuichao Servlet Method:"+request.getMethod());
        int i = 0;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        List<UploadResponse> uploadResponses = new ArrayList<UploadResponse>();
        try{
            for(i=0;i<5;i++){
                UploadResponse uploadResponse = new UploadResponse();
                uploadResponse.setId(String.valueOf(i));
                uploadResponse.setPic("holder.js/200x185111");
                uploadResponse.setTitle("万达广场真正的一室一厅，设施全，看好来电");
                uploadResponse.setPrice("500");
                uploadResponse.setArea("100");
                uploadResponse.setBed("1");
                uploadResponse.setBaths("2");
                uploadResponse.setCarport("3");
                uploadResponse.setDistrict("东南区");
                uploadResponse.setStatus("正在出售");
                uploadResponse.setAddress("捷达大路1777号");
                uploadResponse.setDescription("捷...");

                uploadResponses.add(uploadResponse);
            }
        }catch (Exception e) {
            logger.error(e);
            throw new ServletException(e);
        } finally {
            Map<String, List<UploadResponse>> ret = new HashMap<String, List<UploadResponse>>();
            ret.put("items", uploadResponses);
            String responseContent = objectMapper.writeValueAsString(ret);
//            String responseContent = objectMapper.writeValueAsString(uploadResponses);

            logger.info("Response content Cuichao: " + responseContent);

            printWriter.write(responseContent);
            printWriter.close();
        }

    }
    private static class UploadResponse {
        private String id;
        private String Pic;
        private String Title;
        private String Price;
        private String Area;
        private String Bed;
        private String Baths;
        private String Carport;
        private String District;
        private String Status;
        private String Address;
        private String Description;

        public void setId(String id) {
            this.id = id;
        }

        public void setPic(String pic) {
            Pic = pic;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public void setArea(String area) {
            Area = area;
        }

        public void setBed(String bed) {
            Bed = bed;
        }

        public void setBaths(String baths) {
            Baths = baths;
        }

        public void setCarport(String carport) {
            Carport = carport;
        }

        public void setDistrict(String district) {
            District = district;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public void setDescription(String description) {
            Description = description;
        }



        public String getId() {
            return id;
        }

        public String getPic() {
            return Pic;
        }

        public String getTitle() {
            return Title;
        }

        public String getPrice() {
            return Price;
        }

        public String getArea() {
            return Area;
        }

        public String getBed() {
            return Bed;
        }

        public String getBaths() {
            return Baths;
        }

        public String getCarport() {
            return Carport;
        }

        public String getDistrict() {
            return District;
        }

        public String getStatus() {
            return Status;
        }

        public String getAddress() {
            return Address;
        }

        public String getDescription() {
            return Description;
        }

    }
}
