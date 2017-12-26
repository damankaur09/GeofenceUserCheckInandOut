package com.android.maplocation.parceable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daman on 12/19/2017.
 */

public class OfficeLocations implements Parcelable {

    private double longitude;
    private double latitude;
    private String key;

    public OfficeLocations(double latitude, double longitude,String key) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.key=key;
    }

    public String getKey() {
        return key;
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
        dest.writeString(this.key);
    }

    protected OfficeLocations(Parcel in) {
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.key = in.readString();
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
