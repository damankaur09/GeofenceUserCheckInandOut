package com.android.maplocation.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daman on 12/19/2017.
 */

public class OfficeLocations implements Parcelable {

    private double longitude;
    private double latitude;

    public OfficeLocations(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
    }

    protected OfficeLocations(Parcel in) {
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
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
