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
    private String startDate,endDate,weeklogDay;
    public ReportData(String date, String location, String inTime, String outTime, String totalhours) {
        this.date = date;
        this.location = location;
        this.inTime = inTime;
        this.outTime = outTime;
        this.totalhours = totalhours;
    }

    public ReportData(String date, String location, String inTime, String outTime, String totalhours,String startDate,String endDate,String weeklogDay) {
        this.date = date;
        this.location = location;
        this.inTime = inTime;
        this.outTime = outTime;
        this.totalhours = totalhours;
        this.startDate=startDate;
        this.endDate=endDate;
        this.weeklogDay=weeklogDay;
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

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getWeeklogDay() {
        return weeklogDay;
    }
}
