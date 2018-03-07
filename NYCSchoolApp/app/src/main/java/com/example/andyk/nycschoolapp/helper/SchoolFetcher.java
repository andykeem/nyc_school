package com.example.andyk.nycschoolapp.helper;

import android.net.Uri;
import android.util.Log;

import com.example.andyk.nycschoolapp.model.School;
import com.example.andyk.nycschoolapp.model.SchoolDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andyk on 3/7/18.
 */

public class SchoolFetcher extends HttpRequest {

    protected static final String TAG = SchoolFetcher.class.getSimpleName();

    protected static final String HOST = "https://data.cityofnewyork.us";
    protected static final String PATH = "resource/97mf-9njv.json";
    protected static final String PARAM_SELECT_KEY = "$select";
    protected static final String PARAM_SELECT_VALUE = "dbn,school_name";

    protected Uri.Builder getBaseUri() {
        return Uri.parse(HOST).buildUpon();
    }

    protected Uri getRequestUri() {
        Uri.Builder baseUri = this.getBaseUri();
        baseUri.appendEncodedPath(PATH);
        Uri uri = baseUri.appendQueryParameter(PARAM_SELECT_KEY, PARAM_SELECT_VALUE)
                .build();
        return uri;
    }

    protected String getRequestUrl() {
        return this.getRequestUri().toString();
    }

    public List<School> fetchSchools() {
        String url = this.getRequestUrl();
        String resp = this.getUrlString(url);
        List schools = this.getSchools(resp);
        return schools;
    }

    protected List<School> getSchools(String resp) {
        List<School> schools = new ArrayList<>();
        JSONTokener tokener = new JSONTokener(resp);
        JSONArray arr = null;
        try {
            arr = (JSONArray) tokener.nextValue();
            int numItems = arr.length();
            for (int i = 0; i < numItems; i++) {
                JSONObject json = (JSONObject) arr.get(i);
                if (json != null) {
                    School school = new School();
                    school.setDbn(json.getString("dbn"));
                    school.setName(json.getString("school_name"));
                    schools.add(school);
                }
            }
        } catch (JSONException je) {
            Log.e(TAG, je.getMessage(), je);
        }
        return schools;
    }
}
