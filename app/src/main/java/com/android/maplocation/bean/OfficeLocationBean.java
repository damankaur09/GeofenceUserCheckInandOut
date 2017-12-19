package com.android.maplocation.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Daman on 12/19/2017.
 */

public class OfficeLocationBean extends GenericResponseBean
{


    @SerializedName("data")
    private List<Locations> DataBean;

    public List<Locations> getDataBean() {
        return DataBean;
    }

    public void setDataBean(List<Locations> dataBean) {
        DataBean = dataBean;
    }
}
