package com.example.andyk.nycschoolapp.model;

/**
 * Created by andyk on 3/7/18.
 */

public class SchoolDetail extends School {
    protected String mSatMath;
    protected String mSatReading;
    protected String mSatWriting;

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
}
