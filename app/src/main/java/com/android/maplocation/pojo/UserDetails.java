package com.android.maplocation.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daman on 12/26/2017.
 */

public class UserDetails implements Parcelable {
   String userid;

    public UserDetails(String userid) {
        this.userid = userid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userid);
    }

    protected UserDetails(Parcel in) {
        this.userid = in.readString();
    }

    public static final Parcelable.Creator<UserDetails> CREATOR = new Parcelable.Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel source) {
            return new UserDetails(source);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };
}
