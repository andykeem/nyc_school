package com.example.andyk.nycschoolapp.model;

/**
 * Created by andyk on 3/7/18.
 */

public class School {
    protected String mDbn;
    protected String mName;

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
