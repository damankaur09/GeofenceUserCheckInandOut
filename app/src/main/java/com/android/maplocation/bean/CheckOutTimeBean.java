package com.android.maplocation.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Daman on 12/26/2017.
 */

public class CheckOutTimeBean extends GenericResponseBean
{
    /**
     * updated_id : 28
     */

    @SerializedName("updated_id")
    private String updatedId;



    public String getUpdatedId() {
        return updatedId;
    }

    public void setUpdatedId(String updatedId) {
        this.updatedId = updatedId;
    }
}
