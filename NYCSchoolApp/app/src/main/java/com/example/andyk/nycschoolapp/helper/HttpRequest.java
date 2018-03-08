package com.example.andyk.nycschoolapp.helper;

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

public class HttpRequest {

    protected static final String TAG = HttpRequest.class.getSimpleName();
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
            int bytesRead = in.read(bytes);
            while (bytesRead != -1) {
                out.write(bytes, 0, bytesRead);
                bytesRead = in.read(bytes);
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
}
