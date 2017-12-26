package com.android.maplocation.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Daman on 12/19/2017.
 */

public class Locations
{


    @SerializedName("sitelocation_id")
    @Expose
    private int sitelocation_id;
    @SerializedName("site_name")
    @Expose
    private String site_name;
    @SerializedName("site_address")
    @Expose
    private String site_address;
    @SerializedName("lattitude")
    @Expose
    private double lattitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;

    public int getSitelocation_id() {
        return sitelocation_id;
    }

    public void setSitelocation_id(int sitelocation_id) {
        this.sitelocation_id = sitelocation_id;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getSite_address() {
        return site_address;
    }

    public void setSite_address(String site_address) {
        this.site_address = site_address;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
