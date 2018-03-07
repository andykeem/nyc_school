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
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    protected static final String TAG = MainFragment.class.getSimpleName();
    protected RecyclerView mListSchool;
    protected List<School> mSchools;

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
        mListSchool = v.findViewById(R.id.list_school);
        mListSchool.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return v;
    }

    public static Fragment newFragment() {
        Fragment f = new MainFragment();
        return f;
    }

    protected void updateUI() {
        if (mSchools.isEmpty()) {
            return;
        }
        mListSchool.setAdapter(new ListSchoolAdapter());
    }

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

    private class SchoolFetchTask extends AsyncTask<Void, Void, List> {
        @Override
        protected List<School> doInBackground(Void... params) {
//            String url = "https://www.google.com/";
//            String response = new HttpRequest().getUrlString(url);

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
