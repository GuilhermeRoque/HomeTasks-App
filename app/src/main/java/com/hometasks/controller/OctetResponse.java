package com.hometasks.controller;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class OctetResponse {
    private boolean status;
    private byte[] value;

    public OctetResponse(HttpURLConnection httpURLConnection) {
        try {
            int responseCode = httpURLConnection.getResponseCode();
            status = responseCode >= 200 && responseCode < 300;
            InputStream inputStream = httpURLConnection.getInputStream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len;
            // read bytes from the input stream and store them in buffer
            while ((len = inputStream.read(buffer)) != -1) {
                // write bytes from the buffer into output stream
                os.write(buffer, 0, len);
            }
            value = os.toByteArray();
            Log.i("IMGLEN",String.valueOf(value.length));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

}
