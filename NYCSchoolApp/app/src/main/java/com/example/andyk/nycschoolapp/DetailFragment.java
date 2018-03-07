package com.example.andyk.nycschoolapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andyk.nycschoolapp.helper.SchoolDetailFetcher;
import com.example.andyk.nycschoolapp.model.SchoolDetail;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    protected static final String TAG = DetailFragment.class.getSimpleName();
    protected static final String ARG_DBN = "dbn";
    protected ActionBar mActionBar;
    protected SchoolDetail mDetail;
    protected TextView mTvScoreMath;
    protected TextView mTvScoreReading;
    protected TextView mTvScoreWriting;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();
        String schoolDbn = args.getString(ARG_DBN);
        String[] params = new String[]{schoolDbn};
        new SchoolDetailTask().execute(params);
        mActionBar = ((AppCompatActivity) this.getActivity()).getSupportActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        mTvScoreMath = v.findViewById(R.id.tv_score_math);
        mTvScoreReading = v.findViewById(R.id.tv_score_reading);
        mTvScoreWriting = v.findViewById(R.id.tv_score_writing);
        return v;
    }

    public void updateUI() {
        String notAvailable = this.getResources().getString(R.string.not_available);
        if ((mDetail != null) && !TextUtils.isEmpty(mDetail.getName())) {
            mActionBar.setTitle(mDetail.getName());
        }
        if ((mDetail != null) && !TextUtils.isEmpty(mDetail.getSatMath())) {
            mTvScoreMath.setText(mDetail.getSatMath());
        } else {
            mTvScoreMath.setText(notAvailable);
        }
        if ((mDetail != null) && !TextUtils.isEmpty(mDetail.getSatReading())) {
            mTvScoreReading.setText(mDetail.getSatReading());
        } else {
            mTvScoreReading.setText(notAvailable);
        }
        if ((mDetail != null) && !TextUtils.isEmpty(mDetail.getSatWriting())) {
            mTvScoreWriting.setText(mDetail.getSatWriting());
        } else {
            mTvScoreWriting.setText(notAvailable);
        }
    }

    public static Fragment newFragment(String schoolDbn) {
        Bundle args = new Bundle();
        args.putString(ARG_DBN, schoolDbn);
        Fragment frgmnt = new DetailFragment();
        frgmnt.setArguments(args);
        return frgmnt;
    }

    private class SchoolDetailTask extends AsyncTask<String, Void, SchoolDetail> {
        @Override
        protected SchoolDetail doInBackground(String... params) {
            String schoolDbn = params[0];
            SchoolDetail detail = new SchoolDetailFetcher().getDetailByDbn(schoolDbn);
            return detail;
        }
        @Override
        protected void onPostExecute(SchoolDetail result) {
            mDetail = result;
            updateUI();
        }
    }
}
