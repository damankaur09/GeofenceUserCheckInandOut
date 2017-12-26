package com.android.maplocation.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Daman on 12/26/2017.
 */

public class CheckInTimeBean extends GenericResponseBean
{

    /**
     * data : {"user_id":"10","first_name":"damanpreet","last_name":"kaur","email":"daman@gmail.com","password":"123456","address":"Kharar","country":"India","state":"punjab","city":"Mohali","user_type":"2","device_token":"123456789","device_type":"android"}
     */

    @SerializedName("data")
    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 10
         * first_name : damanpreet
         * last_name : kaur
         * email : daman@gmail.com
         * password : 123456
         * address : Kharar
         * country : India
         * state : punjab
         * city : Mohali
         * user_type : 2
         * device_token : 123456789
         * device_type : android
         */

        @SerializedName("user_id")
        private String userId;
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("last_name")
        private String lastName;
        @SerializedName("email")
        private String email;
        @SerializedName("password")
        private String password;
        @SerializedName("address")
        private String address;
        @SerializedName("country")
        private String country;
        @SerializedName("state")
        private String state;
        @SerializedName("city")
        private String city;
        @SerializedName("user_type")
        private String userType;
        @SerializedName("device_token")
        private String deviceToken;
        @SerializedName("device_type")
        private String deviceType;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }
    }
}
