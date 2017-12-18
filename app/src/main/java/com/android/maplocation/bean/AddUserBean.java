package com.android.maplocation.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daman on 12/18/2017.
 */

public class AddUserBean extends GenericResponseBean
{
    /*{
        "status": 200,
            "status_message": "Added User",
            "data": {
        "user_id": 7,
                "unique_key": "8oVRJTihnk",
                "device_key": "",
                "device_type": ""
    }
    }*/

    @SerializedName("data")
    private String DataBean;

    public String getDataBean() {
        return DataBean;
    }

    public void setDataBean(String dataBean) {
        DataBean = dataBean;
    }

    public static class DataBean
    {
        @SerializedName("unique_key")
        private String unique_key;
        @SerializedName("device_key")
        private String device_key;
        @SerializedName("device_type")
        private String device_type;

        public String getUnique_key() {
            return unique_key;
        }

        public void setUnique_key(String unique_key) {
            this.unique_key = unique_key;
        }

        public String getDevice_key() {
            return device_key;
        }

        public void setDevice_key(String device_key) {
            this.device_key = device_key;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }
    }

}
