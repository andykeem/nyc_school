package com.example.andyk.nycschoolapp.helper;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andyk.nycschoolapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SchoolFetchTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);




        return v;
    }

    public static Fragment newFragment() {
        Fragment f = new MainFragment();
        return f;
    }

    protected void updateUI() {

    }

    private class SchoolFetchTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
//            String url = "https://www.google.com/";
//            String response = new HttpRequest().getUrlString(url);

            String response = new SchoolFetcher().fetchSchools();
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            updateUI();
        }
    }
}
