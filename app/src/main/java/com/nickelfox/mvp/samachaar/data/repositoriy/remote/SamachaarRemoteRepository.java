package com.nickelfox.mvp.samachaar.data.repositoriy.remote;

import android.view.Display;

import androidx.annotation.NonNull;

import com.nickelfox.mvp.samachaar.data.repositoriy.model.Model;
import com.nickelfox.mvp.samachaar.data.repositoriy.model.SamachaarArticle;
import com.nickelfox.mvp.samachaar.data.repositoriy.remote.api.ApiClient;
import com.nickelfox.mvp.samachaar.data.repositoriy.remote.api.ApiService;

import java.net.UnknownHostException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SamachaarRemoteRepository {

    private ApiService apiService = ApiClient.getService().create(ApiService.class);

    private static SamachaarRemoteRepository INSTANCE;

    public static SamachaarRemoteRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SamachaarRemoteRepository();
        }
        return INSTANCE;
    }

    public interface LoadSamachaarCallBack {
        void onSamachaarLoaded(@NonNull List<SamachaarArticle> list);

        void onDataNotAvailable(@NonNull String errorMessage);

    }

    public void getSamachaarList(@NonNull final LoadSamachaarCallBack callBack, String category, String country, String language) {
        final Call<Model> modelCall = apiService.getModelList(category, ApiClient.sKey, country, language);
        modelCall.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model model = response.body();

                callBack.onSamachaarLoaded(model.getSamachaarArticleList());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                if (t instanceof UnknownHostException) {
                    callBack.onDataNotAvailable("No Internet");
                } else {
                    callBack.onDataNotAvailable(t.getLocalizedMessage());
                }
            }
        });

    }


}
