package com.example.andyk.nycschoolapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * School subclass that sets school's detail (most likely SAT scores for now) information
 */
public class SchoolDetail extends School implements Parcelable {

    protected String mSatMath;
    protected String mSatReading;
    protected String mSatWriting;
    protected String mNumSatTakers; // num_of_sat_test_takers

    public static final Parcelable.Creator<SchoolDetail> CREATOR
            = new Parcelable.Creator<SchoolDetail>() {
        public SchoolDetail createFromParcel(Parcel in) {
            return new SchoolDetail(in);
        }
        public SchoolDetail[] newArray(int size) {
            return new SchoolDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSatMath);
        dest.writeString(mSatReading);
        dest.writeString(mSatWriting);
        dest.writeString(mNumSatTakers);
    }

    public SchoolDetail(Parcel in) {
        mSatMath = in.readString();
        mSatReading = in.readString();
        mSatWriting = in.readString();
        mNumSatTakers = in.readString();
    }

    public SchoolDetail() {

    }

    public String getSatMath() {
        return mSatMath;
    }

    public void setSatMath(String satMath) {
        mSatMath = satMath;
    }

    public String getSatReading() {
        return mSatReading;
    }

    public void setSatReading(String satReading) {
        mSatReading = satReading;
    }

    public String getSatWriting() {
        return mSatWriting;
    }

    public void setSatWriting(String satWriting) {
        mSatWriting = satWriting;
    }

    public String getNumSatTakers() {
        return mNumSatTakers;
    }

    public void setNumSatTakers(String numSatTakers) {
        this.mNumSatTakers = numSatTakers;
    }
}
