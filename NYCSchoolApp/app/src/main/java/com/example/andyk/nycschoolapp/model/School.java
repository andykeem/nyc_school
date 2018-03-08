package com.example.andyk.nycschoolapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * School class that's being used for list view
 */

public class School implements Parcelable {

    protected String mDbn;
    protected String mName;

    public static final Parcelable.Creator<School> CREATOR
            = new Parcelable.Creator<School>() {
        public School createFromParcel(Parcel in) {
            return new School(in);
        }

        public School[] newArray(int size) {
            return new School[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDbn);
        dest.writeString(mName);
    }

    public School(Parcel in) {
        mDbn = in.readString();
        mName = in.readString();
    }

    public School() {

    }

    public String getDbn() {
        return mDbn;
    }

    public void setDbn(String dbn) {
        mDbn = dbn;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
