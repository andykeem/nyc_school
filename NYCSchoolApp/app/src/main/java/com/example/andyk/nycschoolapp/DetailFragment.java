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
 * Uses AsyncTask to fetch school detail info based on dbn argument and renders the UI once the
 * response comes back.
 */
public class DetailFragment extends Fragment {

    protected static final String TAG = DetailFragment.class.getSimpleName();
    protected static final String ARG_DBN = "dbn";
    protected ActionBar mActionBar;
    protected SchoolDetail mDetail;
    protected TextView mTvSchooName;
    protected TextView mTvScoreMath;
    protected TextView mTvScoreReading;
    protected TextView mTvScoreWriting;
    protected TextView mTvNumSatTakers;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * gets the dbn parameter that passed to this once user clicks the school listing item from
     * previous screen (MainActivity)
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        Bundle args = this.getArguments();
        String schoolDbn = args.getString(ARG_DBN);
        String[] params = new String[]{schoolDbn};
        new SchoolDetailTask().execute(params);
        mActionBar = ((AppCompatActivity) this.getActivity()).getSupportActionBar();
    }

    /**
     * find each view by id and sets to instance variables
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        mTvSchooName = v.findViewById(R.id.tv_school_name);
        mTvScoreMath = v.findViewById(R.id.tv_score_math);
        mTvScoreReading = v.findViewById(R.id.tv_score_reading);
        mTvScoreWriting = v.findViewById(R.id.tv_score_writing);
        mTvNumSatTakers = v.findViewById(R.id.tv_num_sat_takers);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // make both School and SchoolDetail clases Parcelable





    }

    /**
     * updates UI once the API request comes back
     */
    public void updateUI() {
        String notAvailable = this.getResources().getString(R.string.not_available);
        if ((mDetail != null) && !TextUtils.isEmpty(mDetail.getName())) {
            mActionBar.setTitle(mDetail.getName());
            mTvSchooName.setText(mDetail.getName());
        } else {
            mTvSchooName.setVisibility(View.GONE);
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
        if ((mDetail != null) && !TextUtils.isEmpty(mDetail.getNumSatTakers())) {
            mTvNumSatTakers.setText(mDetail.getNumSatTakers());
        } else {
            mTvNumSatTakers.setText(notAvailable);
        }
    }

    /**
     * helping static methods that sets the school's "dbn" value as fragment argument returns
     * that fragment
     * @param schoolDbn
     * @return
     */
    public static Fragment newFragment(String schoolDbn) {
        Bundle args = new Bundle();
        args.putString(ARG_DBN, schoolDbn);
        Fragment frgmnt = new DetailFragment();
        frgmnt.setArguments(args);
        return frgmnt;
    }

    /**
     * short background thread that fetchs school detail information
     */
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
