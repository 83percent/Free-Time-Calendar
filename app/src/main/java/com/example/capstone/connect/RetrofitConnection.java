package com.example.capstone.connect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnection {
    private Gson gson = new GsonBuilder().setLenient().create();
    // private static final String baseUrl = "http://10.0.2.2:3001";
    private static final String baseUrl = "http://34.84.172.217:3001";
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    public HTTPService server = retrofit.create(HTTPService.class);
    // Singleton
    private static RetrofitConnection instance = null;
    private RetrofitConnection() {}
    public static RetrofitConnection getInstance() {
        if(instance == null)  { instance = new RetrofitConnection(); }
        return instance;
    }
}