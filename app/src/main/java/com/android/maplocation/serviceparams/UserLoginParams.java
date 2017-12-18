package com.android.maplocation.serviceparams;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daman on 12/18/2017.
 */

public class UserLoginParams
{
    /*{"task":"getUserDetails",
            "email":"ratio.vishakha@gmail.com",
            "password":"12345",
            "device_key":"123456789",
            "device_type":"android",
            "usertimeZone":"Asia/Kolkata"}*/

    @SerializedName("task")
    private String task;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
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
}
