package com.android.maplocation.serviceparams;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daman on 12/19/2017.
 */

public class OfficeLocationParams
{
    @SerializedName("task")
    private String task;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
