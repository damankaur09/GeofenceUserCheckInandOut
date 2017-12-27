package com.android.maplocation.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daman on 12/19/2017.
 */

public class OfficeLocations implements Parcelable {

    private double longitude;
    private double latitude;
    private int location_id;
    private String locationName;


    public OfficeLocations(double latitude, double longitude,String locationName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName=locationName;
    }

    public OfficeLocations(double latitude, double longitude,int location_id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.location_id=location_id;

    }

    public int getLocation_id() {
        return location_id;
    }

    public String getLocationName() {
        return locationName;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeInt(this.location_id);
        dest.writeString(this.locationName);
    }

    protected OfficeLocations(Parcel in) {
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.location_id=in.readInt();
        this.locationName=in.readString();
    }

    public static final Parcelable.Creator<OfficeLocations> CREATOR = new Parcelable.Creator<OfficeLocations>() {
        @Override
        public OfficeLocations createFromParcel(Parcel source) {
            return new OfficeLocations(source);
        }

        @Override
        public OfficeLocations[] newArray(int size) {
            return new OfficeLocations[size];
        }
    };
}
