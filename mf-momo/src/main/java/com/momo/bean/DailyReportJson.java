package com.momo.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Chao.Cui.VWED on 2015/12/14.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DailyReportJson {
    private String planG;
    private String actualG;
    private String opr;
    private String roa;
    private String dateG;
    private Long dateLong;

    public Long getDateLong() {
        return dateLong;
    }

    public void setDateLong(Long dateLong) {
        this.dateLong = dateLong;
    }

    public String getPlanG() {
        return planG;
    }

    public void setPlanG(String planG) {
        this.planG = planG;
    }

    public String getActualG() {
        return actualG;
    }

    public void setActualG(String actualG) {
        this.actualG = actualG;
    }

    public String getOpr() {
        return opr;
    }

    public void setOpr(String opr) {
        this.opr = opr;
    }

    public String getRoa() {
        return roa;
    }

    public void setRoa(String roa) {
        this.roa = roa;
    }

    public String getDateG() {
        return dateG;
    }

    public void setDateG(String dateG) {
        this.dateG = dateG;
    }
}