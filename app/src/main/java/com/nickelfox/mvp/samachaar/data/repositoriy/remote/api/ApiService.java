package com.nickelfox.mvp.samachaar.data.repositoriy.remote.api;

import com.nickelfox.mvp.samachaar.data.repositoriy.model.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.nickelfox.mvp.samachaar.data.repositoriy.remote.api.ApiRoutes.TOP_HEADLINES;

public interface ApiService {
    @GET(TOP_HEADLINES)
    Call<Model> getModelList(@Query("category") String category, @Query("apiKey") String apiKey, @Query("country") String country, @Query("language") String language);
}
