package com.hometasks.controller;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by user on 12/8/17.
 */

public class Service {

    private static String baseUrl = "http://192.168.0.150:8080/hometasks/api/v1/";
    private static String token;
    public static String login;

    public Service() {
        // This is important. The application may break without this line.
        System.setProperty("jsse.enableSNIExtension", "false");
    }



    private static HttpURLConnection prepareCon(String path, String method) {
        try {
            String urlString = baseUrl.concat(path);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (token != null)
                connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestMethod(method);
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Content-Type","application/json");
            if (!method.equals("GET"))
                connection.setDoOutput(true);
            connection.setDoInput(true);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JsonResponse authUser(String idUsuario, String senha) {
        try {
            String urlString = "login/";

            String credential = idUsuario.concat(":");
            credential = credential.concat(senha);

            HttpURLConnection connection = prepareCon(urlString, "POST");

            String encode = Base64.encodeToString(credential.getBytes(), Base64.NO_WRAP);

            if (connection == null) return null;
            connection.setRequestProperty("Authorization", "Basic " + encode);

            connection.connect();

            Log.i("STATUS", String.valueOf(connection.getResponseCode()));
            Log.i("MSG", connection.getResponseMessage());

            JsonResponse jsonResponse = new JsonResponse(connection);
            connection.disconnect();
            token = jsonResponse.getJsonValue("token");
            if (token != null)
                    login=idUsuario;
            return jsonResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonResponse postObject(Object o) {
        try {
            String path = o.getClass().getSimpleName();
            HttpURLConnection connection = prepareCon(path, "POST");

            Gson gson = new Gson();
            String userString = gson.toJson(o);
            Log.i("JSON", userString);

            if (connection == null) return null;
            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
            os.writeBytes(userString);

            os.flush();
            os.close();

            connection.connect();

            Log.i("STATUS", String.valueOf(connection.getResponseCode()));
            Log.i("MSG", connection.getResponseMessage());

            JsonResponse jsonResponse = new JsonResponse(connection);
            connection.disconnect();

            return jsonResponse;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static JsonResponse getObject(Class<?> entityClass, Class<?> ownerClass, String ownerId) {
        try {

            String simpleName = entityClass.getSimpleName();
            String key = "id";
            if(!entityClass.equals(ownerClass)) {
                key = key.concat(ownerClass.getSimpleName());
            }
            String path = simpleName+"?"+key+"="+ownerId;

            HttpURLConnection connection = prepareCon(path, "GET");

            if (connection == null) return null;

            connection.connect();


            Log.i("STATUS", String.valueOf(connection.getResponseCode()));
            Log.i("MSG", connection.getResponseMessage());

            JsonResponse jsonResponse = new JsonResponse(connection);
            connection.disconnect();

            return jsonResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static OctetResponse getPicture(Class<?> entityClass, String id) {
        try {

            String simpleName = entityClass.getSimpleName();
            String path = "picture" + "/" + simpleName+"?id="+id;
            HttpURLConnection connection = prepareCon(path, "GET");

            if (connection == null) return null;
            connection.connect();


            Log.i("STATUS", String.valueOf(connection.getResponseCode()));
            Log.i("MSG", connection.getResponseMessage());

            OctetResponse octetResponse = new OctetResponse(connection);
            connection.disconnect();

            return octetResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}