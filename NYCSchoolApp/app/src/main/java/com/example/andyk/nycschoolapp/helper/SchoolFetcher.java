package com.example.andyk.nycschoolapp.helper;

import android.net.Uri;

/**
 * Created by andyk on 3/7/18.
 */

public class SchoolFetcher extends HttpRequest {

    protected static final String TAG = SchoolFetcher.class.getSimpleName();
    protected static final String HOST = "https://data.cityofnewyork.us/";
    protected static final String PATH = "Education/DOE-High-School-Directory-2017/s3k6-pzi2";

    protected String getRequestPath() {
        Uri uri = Uri.parse(HOST).buildUpon()
                .appendEncodedPath(PATH)
                .build();
        return uri.toString();
    }

    public String fetchSchools() {
        String path = this.getRequestPath();
        String resp = this.getUrlString(path);
        return resp;
    }
}
