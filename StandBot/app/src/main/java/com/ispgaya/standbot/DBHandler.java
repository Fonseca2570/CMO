package com.ispgaya.standbot;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DBHandler {
    public static final String BASE_URL = "https://hard-and-fast-execu.000webhostapp.com/";
    public static Retrofit retrofit = null;

    public static Retrofit getDBHandler(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
