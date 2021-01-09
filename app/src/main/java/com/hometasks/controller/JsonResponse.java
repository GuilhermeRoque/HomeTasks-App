package com.hometasks.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class JsonResponse {
    private boolean status;
    private JsonElement jsonElement;
    private static Gson gson = new Gson();
    private static JsonParser jsonParser = new JsonParser();

    public JsonResponse(HttpURLConnection httpURLConnection){
        try {
            int responseCode = httpURLConnection.getResponseCode();
            status = responseCode >= 200 && responseCode < 300;
            String connectionBody = getString(httpURLConnection.getInputStream());
            if (connectionBody != null){
                jsonElement = jsonParser.parse(connectionBody);
            }
            Log.i("statusHTTPResp", String.valueOf(status));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getString(InputStream inputStream) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            Scanner scanner = new Scanner(inputStreamReader);
            StringBuilder stringBuffer = new StringBuilder();
            while (scanner.hasNext()) {
                stringBuffer.append(scanner.nextLine());
            }
            System.out.println("STRING BUFFER >>>>>>>" + stringBuffer);
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isStatus() {
        return status;
    }

    public String getJsonValue(String key) {
        if (jsonElement != null){
            JsonObject asJsonObject = jsonElement.getAsJsonObject();
            JsonElement value = asJsonObject.get(key);
            String asString = value.getAsString();
            Log.i("JSONValue", asString);
            return asString;
        }
        return null;
    }


    public <T> T getEntity(Class<T> entityClass) {
        if (jsonElement.isJsonObject()){
            JsonObject asJsonObject = jsonElement.getAsJsonObject();
            return gson.fromJson(asJsonObject,entityClass);
        }
        return null;
    }


}
