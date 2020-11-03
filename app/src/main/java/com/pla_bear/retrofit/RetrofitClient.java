package com.pla_bear.retrofit;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pla_bear.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static RetrofitService getApiService(String baseUrl){
        return getInstance(baseUrl).create(RetrofitService.class);
    }

    private static Retrofit getInstance(String baseUrl){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
