package com.example.andyk.nycschoolapp.helper;

import android.net.Uri;
import android.util.Log;

import com.example.andyk.nycschoolapp.model.SchoolDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by andyk on 3/7/18.
 */

public class SchoolDetailFetcher extends SchoolFetcher {

    protected static final String TAG = SchoolDetailFetcher.class.getSimpleName();
    protected static final String PATH = "resource/734v-jeq5.json";
    protected static final String PARAM_DETAIL_KEY = "dbn";

    protected Uri getRequestUri() {
        Uri.Builder baseUri = this.getBaseUri();
        baseUri.appendEncodedPath(PATH);
        for (String key : mParams.keySet()) {
            String val = mParams.get(key);
            baseUri.appendQueryParameter(key, val);
        }
        Uri uri = baseUri.build();
        return uri;
    }

    public SchoolDetail getDetailByDbn(String dbn) {
        this.setParam(PARAM_DETAIL_KEY, dbn);
        String url = this.getRequestUrl();
        String resp = this.getUrlString(url);
        SchoolDetail detail = this.getSchoolDetail(resp);
        return detail;
    }

    protected SchoolDetail getSchoolDetail(String resp) {
        try {
            JSONArray arr = (JSONArray) new JSONTokener(resp).nextValue();
            int numItems = arr.length();
            for (int i = 0; i < numItems; i++) {
                JSONObject json = (JSONObject) arr.get(i);
                if (json != null) {
                    SchoolDetail detail = new SchoolDetail();
                    detail.setDbn(json.getString("dbn"));
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