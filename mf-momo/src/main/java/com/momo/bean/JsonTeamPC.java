package com.momo.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Chao.Cui.VWED on 2015/12/14.
 * 某一天得产量信息
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JsonTeamPC {
    private String NO;
    private String stationId;
    private String plan;
    private String actual;
    private String roa;
    private String opr;
    private String catchTime;
    private String num;
//        private String flag;

    public String getNO() {
        return NO;
    }

    public void setNO(String NO) {
        this.NO = NO;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getRoa() {
        return roa;
    }

    public void setRoa(String roa) {
        this.roa = roa;
    }

    public String getOpr() {
        return opr;
    }

    public void setOpr(String opr) {
        this.opr = opr;
    }

    public String getCatchTime() {
        return catchTime;
    }

    public void setCatchTime(String catchTime) {
        this.catchTime = catchTime;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

/*        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }*/
}