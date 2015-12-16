package com.momo.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Chao.Cui.VWED on 2015/12/14.
 * 停台信息记录
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JsonBreakdown {
    private String NO;
    private String breakNO;
    private String breakLevel;
    private String breakReason;
    private String breakType;
    private String breakName;
    private String startTime;
    private String endTime;
    private String duration;
    private String breakScope;


    public String getNO() {
        return NO;
    }

    public void setNO(String NO) {
        this.NO = NO;
    }

    public String getBreakNO() {
        return breakNO;
    }

    public void setBreakNO(String breakNO) {
        this.breakNO = breakNO;
    }

    public String getBreakLevel() {
        return breakLevel;
    }

    public void setBreakLevel(String breakLevel) {
        this.breakLevel = breakLevel;
    }

    public String getBreakReason() {
        return breakReason;
    }

    public void setBreakReason(String breakReason) {
        this.breakReason = breakReason;
    }

    public String getBreakType() {
        return breakType;
    }

    public void setBreakType(String breakType) {
        this.breakType = breakType;
    }

    public String getBreakName() {
        return breakName;
    }

    public void setBreakName(String breakName) {
        this.breakName = breakName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBreakScope() {
        return breakScope;
    }

    public void setBreakScope(String breakScope) {
        this.breakScope = breakScope;
    }
}