package com.momo.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;


/**
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Planning {
    @JsonProperty(value = "DT_RowId")
    private Long P_Row_id;

    @JsonProperty(value = "Planning_date")
    private Date Planning_date;

    @JsonProperty(value = "Planning_qty")
    private int Planning_qty;

    @JsonProperty(value = "P_Shift_type_id")
    private String P_Shift_type_id;

    @JsonProperty(value = "P_Shift_type")
    private String P_Shift_type;

    @JsonProperty(value = "Cycle_time")
    private float Cycle_time;

    @JsonProperty(value = "cw")
    private String cw;

    public Long getP_Row_id() {
        return P_Row_id;
    }

    public void setP_Row_id(Long p_Row_id) {
        P_Row_id = p_Row_id;
    }

    public Date getPlanning_date() {
        return Planning_date;
    }

    public void setPlanning_date(Date planning_date) {
        Planning_date = planning_date;
    }

    public int getPlanning_qty() {
        return Planning_qty;
    }

    public void setPlanning_qty(int planning_qty) {
        Planning_qty = planning_qty;
    }

    public String getP_Shift_type_id() {
        return P_Shift_type_id;
    }

    public void setP_Shift_type_id(String p_Shift_type_id) {
        P_Shift_type_id = p_Shift_type_id;
    }

    public String getP_Shift_type() {
        return P_Shift_type;
    }

    public void setP_Shift_type(String p_Shift_type) {
        P_Shift_type = p_Shift_type;
    }

    public float getCycle_time() {
        return Cycle_time;
    }

    public void setCycle_time(float cycle_time) {
        Cycle_time = cycle_time;
    }

    public String getCw() {
        return cw;
    }

    public void setCw(String cw) {
        this.cw = cw;
    }
}
