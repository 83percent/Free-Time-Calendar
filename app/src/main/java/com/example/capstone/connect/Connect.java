package com.example.capstone.connect;

import android.util.Log;

import com.example.capstone.bean.ResponseBean;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class Connect {
    //protected final String SERVER = "http://10.0.2.2:3001";
    //protected final String SERVER = "http://34.84.172.217:3001";
    protected final String SERVER = null;
    private final String[] __methods = {"GET","POST","DELETE","PUT","PATCH"};
    private ResponseBean responseBean = null;

    // Singleton
    private static Connect instance = null;
    private Connect() {}
    public static Connect getInstance() {
        if(instance == null) instance = new Connect();
        return instance;
    }

    // Only send 'GET' method
    public ResponseBean send(String path) throws Exception {
        return this.__send(path,"GET", null);
    }

    public ResponseBean send(String path, String method) throws Exception {
        return this.__send(path, method, null);
    }

    // Can't use 'GET' method
    public ResponseBean send(String path, String method, JSONObject data) throws Exception {
        if(method.equals("GET") || !Arrays.asList(__methods).contains((String) method)) return null;
        return this.__send(path, method, data);
    }
    private ResponseBean __send(String path, String method, JSONObject data) throws Exception {
        URL url = new URL(SERVER + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type","application/json; UTF-8");
        connection.setRequestProperty("Accept","application/json;");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setConnectTimeout(6000);

        if(data != null) {
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.toString().getBytes("UTF-8"));
            outputStream.flush();

            outputStream.close();
        }
        Log.d("Connection Test", "connection: "  + connection.getResponseCode());

        BufferedReader br = null;
        try {

            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String inputLine = null;
            StringBuilder responseData = new StringBuilder();
            while((inputLine = br.readLine()) != null) {
                responseData.append(inputLine.trim());
            }
            responseBean = new ResponseBean();
            responseBean.setStatus(connection.getResponseCode());
            responseBean.setData(responseData.toString());
            return responseBean;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(br != null) br.close();
            connection.disconnect();
        }

    }
}
