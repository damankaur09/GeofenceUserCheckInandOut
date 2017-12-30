package com.android.maplocation.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Daman on 12/30/2017.
 */

public class ReportsBean {


    /**
     * status : 200
     * status_message : Record Found
     * data : [{"user_work_log_id":"11","user_id":"10","user_intime":"12:47:19","user_outtime":"12:47:58","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:42","currentdate":"2017-12-30"},{"user_work_log_id":"12","user_id":"10","user_intime":"12:58:17","user_outtime":"12:58:30","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"15:28:44","currentdate":"2017-12-30"},{"user_work_log_id":"13","user_id":"10","user_intime":"13:07:08","user_outtime":"13:07:17","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"-00:00:05","currentdate":"2017-12-30"},{"user_work_log_id":"14","user_id":"10","user_intime":"13:19:36","user_outtime":"13:19:43","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:18","currentdate":"2017-12-30"},{"user_work_log_id":"15","user_id":"10","user_intime":"13:27:39","user_outtime":"13:27:45","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"23:59:55","currentdate":"2017-12-30"},{"user_work_log_id":"16","user_id":"10","user_intime":"13:29:01","user_outtime":"13:29:12","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"23:59:55","currentdate":"2017-12-30"},{"user_work_log_id":"17","user_id":"10","user_intime":"13:34:02","user_outtime":"00:00:00","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:00","currentdate":"2017-12-30"},{"user_work_log_id":"18","user_id":"10","user_intime":"13:35:20","user_outtime":"00:00:00","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:00","currentdate":"2017-12-30"},{"user_work_log_id":"19","user_id":"10","user_intime":"13:52:42","user_outtime":"00:00:00","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:00","currentdate":"2017-12-30"},{"totaldayhours":1514572200}]
     */

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("data")
    @Expose
    private List<DataBean> data;

    public static ReportsBean objectFromData(String str) {

        return new Gson().fromJson(str, ReportsBean.class);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_work_log_id : 11
         * user_id : 10
         * user_intime : 12:47:19
         * user_outtime : 12:47:58
         * site_location_id : 1
         * site_address : Bestech Business Tower
         * site_name : My office
         * username : damanpreet kaur
         * hours : 00:00:42
         * currentdate : 2017-12-30
         * totaldayhours : 1514572200
         */

        @SerializedName("user_work_log_id")
        @Expose
        private String userWorkLogId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("user_intime")
        @Expose
        private String userIntime;
        @SerializedName("user_outtime")
        @Expose
        private String userOuttime;
        @SerializedName("site_location_id")
        @Expose
        private String siteLocationId;
        @SerializedName("site_address")
        @Expose
        private String siteAddress;
        @SerializedName("site_name")
        @Expose
        private String siteName;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("hours")
        @Expose
        private String hours;
        @SerializedName("currentdate")
        @Expose
        private String currentdate;
        @SerializedName("totaldayhours")
        @Expose
        private int totaldayhours;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getUserWorkLogId() {
            return userWorkLogId;
        }

        public void setUserWorkLogId(String userWorkLogId) {
            this.userWorkLogId = userWorkLogId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserIntime() {
            return userIntime;
        }

        public void setUserIntime(String userIntime) {
            this.userIntime = userIntime;
        }

        public String getUserOuttime() {
            return userOuttime;
        }

        public void setUserOuttime(String userOuttime) {
            this.userOuttime = userOuttime;
        }

        public String getSiteLocationId() {
            return siteLocationId;
        }

        public void setSiteLocationId(String siteLocationId) {
            this.siteLocationId = siteLocationId;
        }

        public String getSiteAddress() {
            return siteAddress;
        }

        public void setSiteAddress(String siteAddress) {
            this.siteAddress = siteAddress;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getHours() {
            return hours;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }

        public String getCurrentdate() {
            return currentdate;
        }

        public void setCurrentdate(String currentdate) {
            this.currentdate = currentdate;
        }

        public int getTotaldayhours() {
            return totaldayhours;
        }

        public void setTotaldayhours(int totaldayhours) {
            this.totaldayhours = totaldayhours;
        }
    }
}


