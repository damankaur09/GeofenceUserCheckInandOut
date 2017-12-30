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
     * user_id : 10
     * userTimeZone : Asia/Kolkata
     * currentDate : 2017-12-30
     * weekly_log :
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
}
