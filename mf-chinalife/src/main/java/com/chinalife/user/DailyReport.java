package com.chinalife.user;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Chao.Cui.VWED on 14-10-8.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DailyReport {
    private String planG;
    private String actualG;
    private String TimeG;
    private String dateG;

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

    public String getTimeG() {
        return TimeG;
    }

    public void setTimeG(String timeG) {
        TimeG = timeG;
    }

    public String getDateG() {
        return dateG;
    }

    public void setDateG(String dateG) {
        this.dateG = dateG;
    }
}
