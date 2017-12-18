package com.android.maplocation.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daman on 12/18/2017.
 */

public class GenericResponseBean
{

    /**
         * status : 200
         * status_message : Record Found
         */

        @SerializedName("status")
        private int status;
        @SerializedName("status_message")
        private String statusMessage;

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
    }

