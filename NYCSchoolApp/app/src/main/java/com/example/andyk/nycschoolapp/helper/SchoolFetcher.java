package com.example.andyk.nycschoolapp.helper;

import android.util.Log;

import com.example.andyk.nycschoolapp.model.School;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * class that fetchs nyc high schools listing
 */

public class SchoolFetcher extends HttpRequest {

    protected static final String TAG = SchoolFetcher.class.getSimpleName();
    protected static final String PARAM_SELECT_KEY = "$select";
    protected static final String PARAM_SELECT_VALUE = "dbn,school_name";
    protected static final String PARAM_ORDER_KEY = "$order";
    protected static final String PARAM_ORDER_VALUE = "school_name";
    protected String mPath = "resource/97mf-9njv.json";

    public SchoolFetcher() {
        this.setParam(PARAM_SELECT_KEY, PARAM_SELECT_VALUE);
        this.setParam(PARAM_ORDER_KEY, PARAM_ORDER_VALUE);
    }

    protected String getPath() {
        return mPath;
    }

    public ArrayList<School> fetchSchools() {
        String url = this.getRequestUrl();
        String resp = this.getUrlString(url);
        if (resp == null) {
            return null;
        }
        ArrayList schools = this.getSchools(resp);
        return schools;
    }

    /**
     * parses JSON response and stores each school info (dbn, school_name) to School object and finally
     * return ArrayList of Schools
     * @param resp
     * @return
     */
    protected ArrayList<School> getSchools(String resp) {
        ArrayList<School> schools = new ArrayList<>();
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
                    String name = json.getString("school_name");
                    name = this.cleanSchoolName(name);
                    school.setName(name);
                    schools.add(school);
                }
            }
        } catch (JSONException je) {
            Log.e(TAG, je.getMessage(), je);
        }
        return schools;
    }

    /**
     * reemoves unnecessary character(s)
     * @param str
     * @return
     */
    protected String cleanSchoolName(String str) {
        return str.replaceAll("[^a-zA-Z0-9\\s()]", "");
    }
}
