package com.example.andyk.nycschoolapp.helper;

import android.util.Log;

import com.example.andyk.nycschoolapp.model.SchoolDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * class that fetchs school detail information. most likely school SAT scores which makes the request
 * to "https://data.cityofnewyork.us/resource/97mf-9njv.json". Once response is back we parse the
 * JSON and stores its data to SchoolDetail object and sends to UI.
 */

public class SchoolDetailFetcher extends HttpRequest {

    protected static final String TAG = SchoolDetailFetcher.class.getSimpleName();
    protected static final String PARAM_DETAIL_KEY = "dbn";
    protected String mPath = "resource/734v-jeq5.json";

    protected String getPath() {
        return mPath;
    }

    public SchoolDetail getDetailByDbn(String dbn) {
        this.setParam(PARAM_DETAIL_KEY, dbn);
        String url = this.getRequestUrl();
        String resp = this.getUrlString(url);
        SchoolDetail detail = this.getSchoolDetail(resp);
        return detail;
    }

    /**
     * parses JSON response and stores its data to SchoolDetail object
     * @param resp
     * @return
     */
    protected SchoolDetail getSchoolDetail(String resp) {
        try {
            JSONArray arr = (JSONArray) new JSONTokener(resp).nextValue();
            int numItems = arr.length();
            for (int i = 0; i < numItems; i++) {
                JSONObject json = (JSONObject) arr.get(i);
                if (json != null) {
                    SchoolDetail detail = new SchoolDetail();
                    detail.setDbn(json.getString("dbn"));
                    detail.setNumSatTakers(json.getString("num_of_sat_test_takers"));
                    detail.setSatReading(json.getString("sat_critical_reading_avg_score"));
                    detail.setSatMath(json.getString("sat_math_avg_score"));
                    detail.setSatWriting(json.getString("sat_writing_avg_score"));
                    detail.setName(json.getString("school_name"));
                    return detail;
                }
            }
        } catch (JSONException je) {
            Log.e(TAG, je.getMessage(), je);
        }
        return null;
    }
}
