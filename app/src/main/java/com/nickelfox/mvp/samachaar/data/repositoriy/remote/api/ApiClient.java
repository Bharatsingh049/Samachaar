package com.nickelfox.mvp.samachaar.data.repositoriy.remote.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String sKey = "40ab81231e4c43fd925cdc3a0d08f7f6";
    private static final String sUrl = "https://newsapi.org/v2/";
    private static Retrofit retrofit = null;

    public static Retrofit getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(sUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
