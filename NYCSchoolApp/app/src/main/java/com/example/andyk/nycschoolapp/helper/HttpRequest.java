package com.example.andyk.nycschoolapp.helper;

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class to make the HTTP requests
 * Created by andyk on 3/7/18.
 */

public abstract class HttpRequest {

    protected static final String TAG = HttpRequest.class.getSimpleName();
    protected static final String HOST = "https://data.cityofnewyork.us";
    protected Map<String, String> mParams = new HashMap<>();

    /**
     * uses getUrlBytes(url) method to fetch HTTP request response
     * @param url
     * @return
     */
    public String getUrlString(String url) {
        byte[] bytes = null;
        try {
            bytes = this.getUrlBytes(url);
            if (bytes == null) {
                return null;
            }
            String response = new String(bytes);
            return response;
        } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage(), ioe);
        }
        return null;
    }

    /**
     * uses Android's HttpURLConnection class to fetch the response using String spec ENDPOINT
     * @param spec
     * @return
     * @throws IOException
     */
    public byte[] getUrlBytes(String spec) throws IOException {
        HttpURLConnection conn = null;
        InputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            URL url = new URL(spec);
            conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            in = conn.getInputStream();
            byte[] bytes = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(bytes)) != -1) {
                out.write(bytes, 0, bytesRead);
            }
            return out.toByteArray();
        } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage(), ioe);
        } finally {
            conn.disconnect();
            out.close();
            if (in != null) {
                in.close();
            }
        }
        return null;
    }

    /**
     * sets request parameters to mParams instance variable
     * @param key
     * @param val
     */
    protected void setParam(String key, String val) {
        mParams.put(key, val);
    }

    /**
     * sets ENDPOINT uri. For efficient request we set $select parameter to pull two school fields
     * (dbn and school_name) per school
     * @return
     */
    protected String getRequestUrl() {
        Uri.Builder builder = Uri.parse(this.getHost()).buildUpon();
        builder.appendEncodedPath(this.getPath());
        for (String key : mParams.keySet()) {
            String val = mParams.get(key);
            builder.appendQueryParameter(key, val);
        }
        String url = builder.toString();
        Log.d(TAG, "request url: " + url);
        return url;
    }

    protected String getHost() {
        return HOST;
    }

    protected abstract String getPath();

    /**
     * reemoves unnecessary character(s)
     * @param text
     * @return
     */
    protected String parseString(String text) {
        return text.replaceAll("[^a-zA-Z0-9\\s()]", "");
    }
}
