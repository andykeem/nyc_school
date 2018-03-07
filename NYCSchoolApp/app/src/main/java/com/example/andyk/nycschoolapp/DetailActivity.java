package com.example.andyk.nycschoolapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    protected static final String INTENT_EXTRA_SCHOOL_DBN = "dbn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        FragmentManager fm = this.getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);
        if (f == null) {
            String schoolDbn = this.getIntent().getStringExtra(INTENT_EXTRA_SCHOOL_DBN);
            f = DetailFragment.newFragment(schoolDbn);
            fm.beginTransaction()
                    .add(R.id.fragment_container, f)
                    .commit();
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, DetailActivity.class);
    }
}