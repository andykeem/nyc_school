package com.example.andyk.nycschoolapp.helper;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by andyk on 3/7/18.
 */

public class HttpRequest {

    protected static final String TAG = HttpRequest.class.getSimpleName();

    public String getUrlString(String url) {
        byte[] bytes = null;
        try {
            bytes = this.getUrlBytes(url);
            String response = new String(bytes);
            return response;
        } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage(), ioe);
        }
        return null;
    }

    public byte[] getUrlBytes(String spec) throws IOException {
        URL url = new URL(spec);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return null;
        }
        InputStream in = conn.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int bytesRead = in.read(bytes);
        while (bytesRead != -1) {
            out.write(bytes, 0, bytesRead);
            bytesRead = in.read(bytes);
        }
        conn.disconnect();
        return out.toByteArray();
    }
}
