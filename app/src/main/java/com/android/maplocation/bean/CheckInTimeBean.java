package com.android.maplocation.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Daman on 12/26/2017.
 */

public class CheckInTimeBean extends GenericResponseBean
{

    @SerializedName("data")
    @Expose
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean
    {
        @SerializedName("user_work_log_id")
        private String user_work_log_id;

        public String getUser_work_log_id() {
            return user_work_log_id;
        }

        public void setUser_work_log_id(String user_work_log_id) {
            this.user_work_log_id = user_work_log_id;
        }
    }

}
