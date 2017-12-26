package com.android.maplocation.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Daman on 12/18/2017.
 */

public class UserLoginBean extends GenericResponseBean{


    @SerializedName("data")
    private List<User> DataBean;

    public List<User> getDataBean() {
        return DataBean;
    }

    public void setDataBean(List<User> dataBean) {
        DataBean = dataBean;
    }
}
