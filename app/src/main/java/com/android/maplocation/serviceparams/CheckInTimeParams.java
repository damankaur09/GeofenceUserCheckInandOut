package com.android.maplocation.serviceparams;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Daman on 12/26/2017.
 */

public class CheckInTimeParams {

    /**
     * task : addWorklog
     * user_id : 5
     * site_location_id : 3
     * checkinTime : 2017-12-22 06:00:00
     * checkoutTime : 2017-12-22 15:10:00
     * lattitude : 20.96143961409684
     * longitude : 79.1015625
     * total_hours : 9.17
     * userTimeZone : Asia/Kolkata
     */

    @SerializedName("task")
    private String task;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("site_location_id")
    private int siteLocationId;
    @SerializedName("checkinTime")
    private String checkinTime;
    @SerializedName("checkoutTime")
    private String checkoutTime;
    @SerializedName("lattitude")
    private double lattitude;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("total_hours")
    private double totalHours;
    @SerializedName("userTimeZone")
    private String userTimeZone;


    public static CheckInTimeParams objectFromData(String str) {

        return new Gson().fromJson(str, CheckInTimeParams.class);
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

    public int getSiteLocationId() {
        return siteLocationId;
    }

    public void setSiteLocationId(int siteLocationId) {
        this.siteLocationId = siteLocationId;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(String checkinTime) {
        this.checkinTime = checkinTime;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public String getUserTimeZone() {
        return userTimeZone;
    }

    public void setUserTimeZone(String userTimeZone) {
        this.userTimeZone = userTimeZone;
    }
}
