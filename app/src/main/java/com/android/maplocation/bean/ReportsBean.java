package com.android.maplocation.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Daman on 12/30/2017.
 */

public class ReportsBean {


    /**
     * status : 200
     * status_message : Record Found
     * data : {"StartDate":"","EndDate":"","Total hours":"hii","userlog":[{"user_work_log_id":"1","user_id":"10","user_intime":"18:31:06","user_outtime":"18:51:17","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"01:30:00","user_indate":"2017-12-28","user_outdate":"2017-12-28"},{"user_work_log_id":"2","user_id":"10","user_intime":"18:32:07","user_outtime":"18:32:14","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-28","user_outdate":"2017-12-28"},{"user_work_log_id":"3","user_id":"10","user_intime":"17:15:38","user_outtime":"06:15:38","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"01:30:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"4","user_id":"10","user_intime":"18:33:01","user_outtime":"18:36:21","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"5","user_id":"10","user_intime":"18:48:20","user_outtime":"18:48:28","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"6","user_id":"10","user_intime":"18:49:29","user_outtime":"18:49:31","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"7","user_id":"10","user_intime":"18:49:42","user_outtime":"18:51:15","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"8","user_id":"10","user_intime":"18:52:14","user_outtime":"18:52:17","site_location_id":"2","site_address":"Advocate Society,Sector 49","site_name":"Vishakha Home","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"9","user_id":"10","user_intime":"20:04:26","user_outtime":"20:05:41","site_location_id":"3","site_address":"Plam meadows kharar, bhagoo majra road","site_name":"Daman home","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"10","user_id":"10","user_intime":"20:25:40","user_outtime":"20:25:48","site_location_id":"3","site_address":"Plam meadows kharar, bhagoo majra road","site_name":"Daman home","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"11","user_id":"10","user_intime":"12:47:19","user_outtime":"12:47:58","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:42","user_indate":"2017-12-30","user_outdate":"2017-12-30"},{"user_work_log_id":"12","user_id":"10","user_intime":"12:58:17","user_outtime":"12:58:30","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"15:28:44","user_indate":"2017-12-30","user_outdate":"2017-12-30"},{"user_work_log_id":"13","user_id":"10","user_intime":"13:07:08","user_outtime":"13:07:17","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"-00:00:05","user_indate":"2017-12-30","user_outdate":"2017-12-30"},{"user_work_log_id":"14","user_id":"10","user_intime":"13:19:36","user_outtime":"13:19:43","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:18","user_indate":"2017-12-30","user_outdate":"2017-12-30"},{"user_work_log_id":"15","user_id":"10","user_intime":"13:27:39","user_outtime":"13:27:45","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"23:59:55","user_indate":"2017-12-30","user_outdate":"2017-12-30"},{"user_work_log_id":"16","user_id":"10","user_intime":"13:29:01","user_outtime":"13:29:12","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"23:59:55","user_indate":"2017-12-30","user_outdate":"2017-12-30"},{"user_work_log_id":"20","user_id":"10","user_intime":"19:44:38","user_outtime":"19:44:52","site_location_id":"3","site_address":"Plam meadows kharar, bhagoo majra road","site_name":"Daman home","username":"damanpreet kaur","hours":"01:30:00","user_indate":"2017-12-30","user_outdate":"2017-12-30"}],"totaldayhours":"67:56"}
     */

    @SerializedName("status")
    private int status;
    @SerializedName("status_message")
    private String statusMessage;
    @SerializedName("data")
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * StartDate :
         * EndDate :
         * Total hours : hii
         * userlog : [{"user_work_log_id":"1","user_id":"10","user_intime":"18:31:06","user_outtime":"18:51:17","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"01:30:00","user_indate":"2017-12-28","user_outdate":"2017-12-28"},{"user_work_log_id":"2","user_id":"10","user_intime":"18:32:07","user_outtime":"18:32:14","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-28","user_outdate":"2017-12-28"},{"user_work_log_id":"3","user_id":"10","user_intime":"17:15:38","user_outtime":"06:15:38","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"01:30:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"4","user_id":"10","user_intime":"18:33:01","user_outtime":"18:36:21","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"5","user_id":"10","user_intime":"18:48:20","user_outtime":"18:48:28","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"6","user_id":"10","user_intime":"18:49:29","user_outtime":"18:49:31","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"7","user_id":"10","user_intime":"18:49:42","user_outtime":"18:51:15","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"8","user_id":"10","user_intime":"18:52:14","user_outtime":"18:52:17","site_location_id":"2","site_address":"Advocate Society,Sector 49","site_name":"Vishakha Home","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"9","user_id":"10","user_intime":"20:04:26","user_outtime":"20:05:41","site_location_id":"3","site_address":"Plam meadows kharar, bhagoo majra road","site_name":"Daman home","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"10","user_id":"10","user_intime":"20:25:40","user_outtime":"20:25:48","site_location_id":"3","site_address":"Plam meadows kharar, bhagoo majra road","site_name":"Daman home","username":"damanpreet kaur","hours":"00:00:00","user_indate":"2017-12-29","user_outdate":"2017-12-29"},{"user_work_log_id":"11","user_id":"10","user_intime":"12:47:19","user_outtime":"12:47:58","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:42","user_indate":"2017-12-30","user_outdate":"2017-12-30"},{"user_work_log_id":"12","user_id":"10","user_intime":"12:58:17","user_outtime":"12:58:30","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"15:28:44","user_indate":"2017-12-30","user_outdate":"2017-12-30"},{"user_work_log_id":"13","user_id":"10","user_intime":"13:07:08","user_outtime":"13:07:17","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"-00:00:05","user_indate":"2017-12-30","user_outdate":"2017-12-30"},{"user_work_log_id":"14","user_id":"10","user_intime":"13:19:36","user_outtime":"13:19:43","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"00:00:18","user_indate":"2017-12-30","user_outdate":"2017-12-30"},{"user_work_log_id":"15","user_id":"10","user_intime":"13:27:39","user_outtime":"13:27:45","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"23:59:55","user_indate":"2017-12-30","user_outdate":"2017-12-30"},{"user_work_log_id":"16","user_id":"10","user_intime":"13:29:01","user_outtime":"13:29:12","site_location_id":"1","site_address":"Bestech Business Tower","site_name":"My office","username":"damanpreet kaur","hours":"23:59:55","user_indate":"2017-12-30","user_outdate":"2017-12-30"},{"user_work_log_id":"20","user_id":"10","user_intime":"19:44:38","user_outtime":"19:44:52","site_location_id":"3","site_address":"Plam meadows kharar, bhagoo majra road","site_name":"Daman home","username":"damanpreet kaur","hours":"01:30:00","user_indate":"2017-12-30","user_outdate":"2017-12-30"}]
         * totaldayhours : 67:56
         */

        @SerializedName("StartDate")
        private String StartDate;
        @SerializedName("EndDate")
        private String EndDate;
        @SerializedName("Total hours")
        private String _$TotalHours259; // FIXME check this code
        @SerializedName("totaldayhours")
        private String totaldayhours;
        @SerializedName("userlog")
        private List<UserlogBean> userlog;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(String StartDate) {
            this.StartDate = StartDate;
        }

        public String getEndDate() {
            return EndDate;
        }

        public void setEndDate(String EndDate) {
            this.EndDate = EndDate;
        }

        public String get_$TotalHours259() {
            return _$TotalHours259;
        }

        public void set_$TotalHours259(String _$TotalHours259) {
            this._$TotalHours259 = _$TotalHours259;
        }

        public String getTotaldayhours() {
            return totaldayhours;
        }

        public void setTotaldayhours(String totaldayhours) {
            this.totaldayhours = totaldayhours;
        }

        public List<UserlogBean> getUserlog() {
            return userlog;
        }

        public void setUserlog(List<UserlogBean> userlog) {
            this.userlog = userlog;
        }

        public static class UserlogBean {
            /**
             * user_work_log_id : 1
             * user_id : 10
             * user_intime : 18:31:06
             * user_outtime : 18:51:17
             * site_location_id : 1
             * site_address : Bestech Business Tower
             * site_name : My office
             * username : damanpreet kaur
             * hours : 01:30:00
             * user_indate : 2017-12-28
             * user_outdate : 2017-12-28
             */

            @SerializedName("user_work_log_id")
            private String userWorkLogId;
            @SerializedName("user_id")
            private String userId;
            @SerializedName("user_intime")
            private String userIntime;
            @SerializedName("user_outtime")
            private String userOuttime;
            @SerializedName("site_location_id")
            private String siteLocationId;
            @SerializedName("site_address")
            private String siteAddress;
            @SerializedName("site_name")
            private String siteName;
            @SerializedName("username")
            private String username;
            @SerializedName("hours")
            private String hours;
            @SerializedName("user_indate")
            private String userIndate;
            @SerializedName("user_outdate")
            private String userOutdate;

            public static UserlogBean objectFromData(String str) {

                return new Gson().fromJson(str, UserlogBean.class);
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

            public String getUserIndate() {
                return userIndate;
            }

            public void setUserIndate(String userIndate) {
                this.userIndate = userIndate;
            }

            public String getUserOutdate() {
                return userOutdate;
            }

            public void setUserOutdate(String userOutdate) {
                this.userOutdate = userOutdate;
            }
        }
    }
}


