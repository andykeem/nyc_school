package com.example.andyk.nycschoolapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andyk.nycschoolapp.helper.SchoolFetcher;
import com.example.andyk.nycschoolapp.model.School;

import java.util.List;

/**
 * A simple {@link Fragment} subclass that uses the RecyclerView to render NYC High Schools information.
 * It makes the API request to "https://data.cityofnewyork.us/resource/734v-jeq5.json" and once JSON
 * response is back it parses and stores all schools information in ArrayList of School objects and
 * then updates the recycler adapter based on that list.
 */
public class MainFragment extends Fragment {

    protected static final String TAG = MainFragment.class.getSimpleName();
    protected RecyclerView mListSchool;
    protected List<School> mSchools;
    protected TextView mTvErrMsg;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        new SchoolFetchTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mListSchool = v.findViewById(R.id.list_school);
        mListSchool.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mTvErrMsg = v.findViewById(R.id.tv_err_msg);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);






    }

    public static Fragment newFragment() {
        Fragment f = new MainFragment();
        return f;
    }

    /**
     * sets RecyclerView's ListSchoolAdapter using mSchools list
     */
    protected void updateUI() {
        if ((mSchools == null) || mSchools.isEmpty()) {
            mTvErrMsg.setText(R.string.school_list_err_msg);
            return;
        }
        mListSchool.setAdapter(new ListSchoolAdapter());
    }

    /**
     * custom ViewHolder class for the RecyclerView
     */
    private class SchoolView extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected School mItem;
        protected TextView mTvSchoolName;
        public SchoolView(View itemView) {
            super(itemView);
            mTvSchoolName = itemView.findViewById(R.id.tv_school_name);
            itemView.setOnClickListener(this);
        }
        public void bindItem(School item) {
            mItem = item;
            mTvSchoolName.setText(item.getName());
        }
        @Override
        public void onClick(View v) {
            Intent activity = DetailActivity.newIntent(getContext());
            activity.putExtra(DetailActivity.INTENT_EXTRA_SCHOOL_DBN, mItem.getDbn());
            startActivity(activity);
        }
    }

    /**
     * custom RecyclerView adapter to list highschool names
     */
    private class ListSchoolAdapter extends RecyclerView.Adapter<SchoolView> {
        @Override
        public int getItemCount() {
            return mSchools.size();
        }
        @Override
        public SchoolView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.school_list_item, parent, false);
            return new SchoolView(itemView);
        }
        @Override
        public void onBindViewHolder(@NonNull SchoolView holder, int position) {
            School item = mSchools.get(position);
            holder.bindItem(item);
        }
    }

    /**
     * background thread to request schools api call
     */
    private class SchoolFetchTask extends AsyncTask<Void, Void, List> {
        @Override
        protected List<School> doInBackground(Void... params) {
            List<School> schools = new SchoolFetcher().fetchSchools();
            return schools;
        }
        @Override
        protected void onPostExecute(List result) {
            mSchools = result;
            updateUI();
        }
    }
}
