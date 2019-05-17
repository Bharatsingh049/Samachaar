package com.nickelfox.mvp.samachaar.allsamachaar;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.nickelfox.mvp.samachaar.data.repositoriy.SamachaarRepository;
import com.nickelfox.mvp.samachaar.data.repositoriy.model.SamachaarArticle;


import java.util.ArrayList;
import java.util.List;

import static com.nickelfox.mvp.samachaar.data.repositoriy.remote.SamachaarRemoteRepository.*;

public class AllSamachaarPresenter implements AllSamachaarContract.Presenter {

    private final SamachaarRepository samachaarRepository;


    private final String[] categories = {"business", "entertainment", "health", "science", "sports", "technology"};

    private AllSamachaarContract.View samachaarView;


    AllSamachaarPresenter(SamachaarRepository samachaarRepository, final AllSamachaarContract.View samachaarView) {
        this.samachaarRepository = samachaarRepository;
        this.samachaarView = samachaarView;

        this.samachaarView.setSamachaarPresenter(this);

        samachaarRepository.getAllSamachaar().observe((AppCompatActivity) samachaarView, new Observer<List<SamachaarArticle>>() {
            @Override
            public void onChanged(List<SamachaarArticle> list) {

                for (int i = 0; i < 6; i++) {
                    List<SamachaarArticle> samachaarArticlesList = new ArrayList<>();

                    for (SamachaarArticle samachaarArticle : list) {
                        if (TextUtils.equals(samachaarArticle.getCategory(), categories[i])) {

                            samachaarArticle.setTitle(samachaarArticle.getTitle());
                            samachaarArticle.setDescription(samachaarArticle.getDescription());
                            samachaarArticle.setUrlToImage(samachaarArticle.getUrlToImage());
                            samachaarArticle.setUrl(samachaarArticle.getUrl());
                            samachaarArticlesList.add(samachaarArticle);
                        }
                    }

                    switch (i) {
                        case 0:
                            int temp = samachaarArticlesList.size();
                            Log.e( "onChanged: ",temp+"" );
                            samachaarView.showBusinessList(samachaarArticlesList);

                            break;
                        case 1:
                            int temp1 = samachaarArticlesList.size();
                            Log.e( "onChanged: ",temp1+"" );
                            samachaarView.showEntertainmentList(samachaarArticlesList);
                            break;
                        case 2:
                            int temp2 = samachaarArticlesList.size();
                            Log.e( "onChanged: ",temp2+"" );
                            samachaarView.showHealthList(samachaarArticlesList);
                            break;
                        case 3:
                            int temp3 = samachaarArticlesList.size();
                            Log.e( "onChanged: ",temp3+"" );
                            samachaarView.showScienceList(samachaarArticlesList);
                            break;
                        case 4:
                            int temp4 = samachaarArticlesList.size();
                            Log.e( "onChanged: ",temp4+"" );
                            samachaarView.showSportsList(samachaarArticlesList);
                            break;
                        case 5:
                            int temp5 = samachaarArticlesList.size();
                            Log.e( "onChanged: ",temp5+"" );
                            samachaarView.showTechnologyList(samachaarArticlesList);
                            break;


                    }

                }
            }
        });
    }


    @Override
    public void start(AllSamachaarContract.View view) {
        if (samachaarView == null) {
            samachaarView = view;
        }
    }

    @Override
    public void onDestroy() {
        samachaarView = null;
    }

    @Override
    public void fetchSamachaar() {
        samachaarView.showLoading();
        samachaarRepository.fetchSamachaar(new LoadSamachaarCallBack() {
            @Override
            public void onSamachaarLoaded(@NonNull List<SamachaarArticle> list) {
                samachaarView.hideLoading();
            }

            @Override
            public void onDataNotAvailable(@NonNull String errorMessage) {
                samachaarView.hideLoading();
                samachaarView.showError(errorMessage);
            }
        });
    }
}
