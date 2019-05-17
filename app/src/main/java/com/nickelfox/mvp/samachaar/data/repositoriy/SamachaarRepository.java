package com.nickelfox.mvp.samachaar.data.repositoriy;

import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.nickelfox.mvp.samachaar.data.repositoriy.local.SamachaarLocalRepository;
import com.nickelfox.mvp.samachaar.data.repositoriy.model.SamachaarArticle;
import com.nickelfox.mvp.samachaar.data.repositoriy.remote.SamachaarRemoteRepository;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;

import static com.nickelfox.mvp.samachaar.data.repositoriy.remote.SamachaarRemoteRepository.*;

public class SamachaarRepository {

    private final String[] categories = {"business", "entertainment", "health", "science", "sports", "technology"};

    private static SamachaarRepository INSTANCE;

    private final SamachaarLocalRepository samachaarLocalRepository;

    private final SamachaarRemoteRepository samachaarRemoteRepository;

    private LiveData<List<SamachaarArticle>> samachaarList;

    private SamachaarRepository(@NonNull SamachaarLocalRepository samachaarLocalRepository
            , @NonNull SamachaarRemoteRepository samachaarRemoteRepository) {
        this.samachaarLocalRepository = samachaarLocalRepository;
        this.samachaarRemoteRepository = samachaarRemoteRepository;
        this.samachaarList = samachaarLocalRepository.getAllSamachaar();
    }


    public static SamachaarRepository getInstance(@NonNull SamachaarLocalRepository samachaarLocalRepository
            , @NonNull SamachaarRemoteRepository samachaarRemoteRepository) {
        if (INSTANCE == null) {
            INSTANCE = new SamachaarRepository(samachaarLocalRepository, samachaarRemoteRepository);
        }
        return INSTANCE;
    }

    public LiveData<List<SamachaarArticle>> getAllSamachaar() {
        return samachaarList;
    }


    public void fetchSamachaar(@NonNull final LoadSamachaarCallBack callBack) {
        samachaarLocalRepository.deleteAllSamachaar(new SamachaarLocalRepository.AsyncDeleteResponseCallBack() {
            @Override
            public void onDeleted(int output) {
                if (output >= 0) {
                    for (int i = 0; i < 6; i++) {
                        final int finalI = i;
                        samachaarRemoteRepository.getSamachaarList(new LoadSamachaarCallBack() {
                            @Override
                            public void onSamachaarLoaded(@NonNull final List<SamachaarArticle> list) {

                                for (int j = 0; j < list.size(); j++) {
                                    SamachaarArticle model = list.get(j);
                                    /*String title = model.getTitle();
                                    String description = model.getDescription();
                                    String urlToImage = model.getUrlToImage();
                                    String url = model.getUrl();*/
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

                                    model.setCategory(categories[finalI]);
                                    list.set(j, model);
                                }
                                samachaarLocalRepository.insertSamachaar(list);
                                callBack.onSamachaarLoaded(list);
                            }

                            @Override
                            public void onDataNotAvailable(@NonNull String errorMessage) {
                                callBack.onDataNotAvailable(errorMessage);
                            }
                        }, categories[i], "In", "en");
                    }
                }
            }
                    });



        }



}
