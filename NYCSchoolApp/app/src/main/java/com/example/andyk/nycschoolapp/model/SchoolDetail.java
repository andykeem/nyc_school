package com.example.andyk.nycschoolapp.model;

/**
 * School subclass that sets school's detail (most likely SAT scores for now) information
 * Created by andyk on 3/7/18.
 */
public class SchoolDetail extends School {
    protected String mSatMath;
    protected String mSatReading;
    protected String mSatWriting;
    protected String mNumSatTakers; // num_of_sat_test_takers

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
