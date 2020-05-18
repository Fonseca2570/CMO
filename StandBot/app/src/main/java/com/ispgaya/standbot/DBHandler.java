package com.ispgaya.standbot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DBHandler {
    public static final String BASE_URL = "https://hard-and-fast-execu.000webhostapp.com/";
    public static Retrofit retrofit = null;

    public static Retrofit getDBHandler(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
