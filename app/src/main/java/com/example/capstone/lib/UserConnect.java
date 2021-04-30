package com.example.capstone.lib;

import com.example.capstone.data.SignUpBean;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UserConnect {
    private String SERVER_URL = "";

    public boolean getUser(String email, String password) {
        JSONObject result = null;
        try {
            URL url = new URL(SERVER_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(6000);

            int responseCode = connection.getResponseCode();
            switch(responseCode) {
                case 200 : {
                    return true;
                }
                default : {
                    return false;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean setUser(SignUpBean bean) {

        return true;
    }

}
