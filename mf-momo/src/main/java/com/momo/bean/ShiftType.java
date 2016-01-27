package com.momo.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by Chao.Cui.VWED on 2016/1/20.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class ShiftType {
    @JsonProperty(value = "DT_RowId")
    private Long Row_id;

    @JsonProperty(value = "Shift_type_id")
    private String Shift_type_id;

    @JsonProperty(value = "Shift_type")
    private String Shift_type;

    @JsonProperty(value = "Shift_id")
    private String Shift_id;

    @JsonProperty(value = "Shift")
    private String Shift;

    @JsonProperty(value = "Shift_start_time")
    private Time Shift_start_time;

    @JsonProperty(value = "Shift_end_time")
    private Time Shift_end_time;

    @JsonProperty(value = "Lunch_start_time")
    private Time Lunch_start_time;

    @JsonProperty(value = "Lunch_end_time")
    private Time Lunch_end_time;

    @JsonProperty(value = "Break_start_time")
    private Time Break_start_time;

    @JsonProperty(value = "Break_end_time")
    private Time Break_end_time;

    @JsonProperty(value = "Earliest_time")
    private Time Earliest_time;

    @JsonProperty(value = "Latest_time")
    private Time Latest_time;

    @JsonProperty(value = "Reset_time")
    private Time Reset_time;

    @JsonProperty(value = "Work_second")
    private String Work_second;

    @JsonProperty(value = "Work_time")
    private String work_time;

    public Long getRow_id() {
        return Row_id;
    }

    public void setRow_id(Long row_id) {
        Row_id = row_id;
    }

    public String getShift_type_id() {
        return Shift_type_id;
    }

    public void setShift_type_id(String shift_type_id) {
        Shift_type_id = shift_type_id;
    }

    public String getShift_type() {
        return Shift_type;
    }

    public void setShift_type(String shift_type) {
        Shift_type = shift_type;
    }

    public String getShift_id() {
        return Shift_id;
    }

    public void setShift_id(String shift_id) {
        Shift_id = shift_id;
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        Shift = shift;
    }

    public Time getShift_start_time() {
        return Shift_start_time;
    }

    public void setShift_start_time(Time shift_start_time) {
        Shift_start_time = shift_start_time;
    }

    public Time getShift_end_time() {
        return Shift_end_time;
    }

    public void setShift_end_time(Time shift_end_time) {
        Shift_end_time = shift_end_time;
    }

    public Time getLunch_start_time() {
        return Lunch_start_time;
    }

    public void setLunch_start_time(Time lunch_start_time) {
        Lunch_start_time = lunch_start_time;
    }

    public Time getLunch_end_time() {
        return Lunch_end_time;
    }

    public void setLunch_end_time(Time lunch_end_time) {
        Lunch_end_time = lunch_end_time;
    }

    public Time getBreak_start_time() {
        return Break_start_time;
    }

    public void setBreak_start_time(Time break_start_time) {
        Break_start_time = break_start_time;
    }

    public Time getBreak_end_time() {
        return Break_end_time;
    }

    public void setBreak_end_time(Time break_end_time) {
        Break_end_time = break_end_time;
    }

    public Time getEarliest_time() {
        return Earliest_time;
    }

    public void setEarliest_time(Time earliest_time) {
        Earliest_time = earliest_time;
    }

    public Time getLatest_time() {
        return Latest_time;
    }

    public void setLatest_time(Time latest_time) {
        Latest_time = latest_time;
    }

    public Time getReset_time() {
        return Reset_time;
    }

    public void setReset_time(Time reset_time) {
        Reset_time = reset_time;
    }

    public String getWork_second() {
        return Work_second;
    }

    public void setWork_second(String work_second) {
        Work_second = work_second;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }
}
