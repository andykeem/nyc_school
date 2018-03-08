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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andyk.nycschoolapp.helper.SchoolFetcher;
import com.example.andyk.nycschoolapp.model.School;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass that uses the RecyclerView to render NYC High Schools information.
 * It makes the API request to "https://data.cityofnewyork.us/resource/734v-jeq5.json" and once JSON
 * response is back it parses and stores all schools information in ArrayList of School objects and
 * then updates the recycler adapter based on that list.
 */
public class MainFragment extends Fragment {

    protected static final String TAG = MainFragment.class.getSimpleName();
    protected static final String STATE_SCHOOLS = "stateSchools";
    protected RecyclerView mListSchool;
    protected ArrayList<School> mSchools;
    protected ArrayList<School> mRandomSchools;
    protected ArrayList<School> mSortedSchools;
    protected TextView mTvErrMsg;
    protected boolean mSorted;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
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
        outState.putParcelableArrayList(STATE_SCHOOLS, mSchools);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // restore saved data
        if (savedInstanceState != null) {
            mSchools = savedInstanceState.getParcelableArrayList(STATE_SCHOOLS);
            this.updateUI();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_top, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * update menu item based on sort / reset
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_sort_alpha).setVisible(!mSorted);
        menu.findItem(R.id.menu_sort_reset).setVisible(mSorted);
    }

    /**
     * top menu handler
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort_alpha: // sorts the listing alphabetically
                mSorted = true;
                if (mSortedSchools == null) {
                    new SchoolSortTask().execute();
                } else {
                    mSchools = mSortedSchools;
                    this.updateUI();
                }
                this.invalidateOptionsMenu();
                return true;
            case R.id.menu_sort_reset: // resets alpahbetic sorting
                mSorted = false;
                if (mRandomSchools == null) {
                    new SchoolFetchTask().execute();
                } else {
                    mSchools = mRandomSchools;
                    this.updateUI();
                }
                this.invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void invalidateOptionsMenu() {
        this.getActivity().invalidateOptionsMenu();
    }

    public static Fragment newFragment() {
        Fragment frgmnt = new MainFragment();
        return frgmnt;
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
    private class SchoolFetchTask extends AsyncTask<Void, Void, ArrayList> {
        @Override
        protected ArrayList<School> doInBackground(Void... params) {
            ArrayList<School> schools = new SchoolFetcher().fetchSchools();
            return schools;
        }
        @Override
        protected void onPostExecute(ArrayList result) {
            mSchools = result;
            mRandomSchools = mSchools;
            updateUI();
        }
    }

    private class SchoolSortTask extends AsyncTask<Void, Void, ArrayList<School>> {
        @Override
        protected ArrayList<School> doInBackground(Void... params) {
            ArrayList<School> schools = new SchoolFetcher().fetchSortedSchools();
            return schools;
        }
        @Override
        protected void onPostExecute(ArrayList<School> result) {
            mSchools = result;
            mSortedSchools = mSchools;
            updateUI();
        }
    }
}
