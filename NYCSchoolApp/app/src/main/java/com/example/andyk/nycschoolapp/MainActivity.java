package com.example.andyk.nycschoolapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    /**
     * Main activity class that uses MainFragment to render list content
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);
        if (f == null) {
            f = MainFragment.newFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, f)
                    .commit();
        }
    }

    /**
     * helper method that returns current activity as an intent
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}