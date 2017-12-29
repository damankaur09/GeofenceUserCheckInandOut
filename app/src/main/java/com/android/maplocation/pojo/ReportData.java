package com.android.maplocation.pojo;

/**
 * Created by Daman on 12/29/2017.
 */

public class ReportData {

    private String date;
    private String location;
    private String inTime;
    private String outTime;
    private String totalhours;

    public ReportData(String date, String location, String inTime, String outTime, String totalhours) {
        this.date = date;
        this.location = location;
        this.inTime = inTime;
        this.outTime = outTime;
        this.totalhours = totalhours;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getInTime() {
        return inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public String getTotalhours() {
        return totalhours;
    }
}
