package com.nickelfox.mvp.samachaar.data.repositoriy.remote;


import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    private final String[] categories = {"business", "entertainment", "health", "science", "sports", "technology"};

    private ApiService apiService = ApiClient.getService().create(ApiService.class);

    private MutableLiveData<List<SamachaarArticle>> mutableLiveData = new MutableLiveData<>();

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



    public void getSamachaarList(/*@NonNull final LoadSamachaarCallBack callBack,*/ final String category, String country, String language) {


        //for (int i = 0; i < 6; i++) {
            final Call<Model> modelCall = apiService.getModelList(category, ApiClient.sKey, country, language);
            //final int finalI = i;
            modelCall.enqueue(new Callback<Model>() {
                @Override
                public void onResponse(@NonNull Call<Model> call, @NonNull Response<Model> response) {
                    Model model = response.body();

                    if (model != null) {
                        setList(model.getSamachaarArticleList(),category);
                        //callBack.onSamachaarLoaded(model.getSamachaarArticleList());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Model> call, @NonNull Throwable t) {
                    if (t instanceof UnknownHostException) {

                        //callBack.onDataNotAvailable("No Internet");
                    } else {
                        //callBack.onDataNotAvailable(t.getLocalizedMessage());
                    }
                    setList(null,null);
                }
            });
        //}


    }

    public MutableLiveData<List<SamachaarArticle>> getAllSamachaar() {
        return mutableLiveData;
    }

    private void setList(List<SamachaarArticle> list, String cat){
        if (list != null) {
            List<SamachaarArticle> samachaarList = mutableLiveData.getValue();

            if (samachaarList != null) {
                samachaarList.addAll(list);
            }


            for (int j = 0; j < list.size(); j++) {
                SamachaarArticle model = list.get(j);
                if (TextUtils.isEmpty(model.getDescription())) {
                    model.setDescription("N/A");
                }
                if (TextUtils.isEmpty(model.getTitle())) {
                    model.setTitle("N/A");
                }
                if (TextUtils.isEmpty(model.getUrlToImage())) {
                    model.setUrlToImage("N/A");
                }
                if (TextUtils.isEmpty(model.getUrl())) {
                    model.setUrl("N/A");
                }

                model.setCategory(cat);
                list.set(j, model);
            }
            mutableLiveData.setValue(samachaarList);
        }
        //samachaarLocalRepository.insertSamachaar(list);

    }

}
