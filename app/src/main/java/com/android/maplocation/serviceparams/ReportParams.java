package com.android.maplocation.serviceparams;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Daman on 12/30/2017.
 */

public class ReportParams
{


    /**
     * task : getWorklog
     * user_id : 2
     * userTimeZone : Asia/Kolkata
     * currentDate :
     * weekly_log :
     * monthly_log : 1
     * month : 12
     * year : 2017
     */

    @SerializedName("task")
    private String task;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("userTimeZone")
    private String userTimeZone;
    @SerializedName("currentDate")
    private String currentDate;
    @SerializedName("weekly_log")
    private String weeklyLog;
    @SerializedName("monthly_log")
    private String monthlyLog;
    @SerializedName("month")
    private String month;
    @SerializedName("year")
    private String year;

    public static ReportParams objectFromData(String str) {

        return new Gson().fromJson(str, ReportParams.class);
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserTimeZone() {
        return userTimeZone;
    }

    public void setUserTimeZone(String userTimeZone) {
        this.userTimeZone = userTimeZone;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getWeeklyLog() {
        return weeklyLog;
    }

    public void setWeeklyLog(String weeklyLog) {
        this.weeklyLog = weeklyLog;
    }

    public String getMonthlyLog() {
        return monthlyLog;
    }

    public void setMonthlyLog(String monthlyLog) {
        this.monthlyLog = monthlyLog;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
