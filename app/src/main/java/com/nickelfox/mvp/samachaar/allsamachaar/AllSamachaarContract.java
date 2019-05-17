package com.nickelfox.mvp.samachaar.allsamachaar;

import androidx.annotation.NonNull;

import com.nickelfox.mvp.samachaar.BasePresenter;
import com.nickelfox.mvp.samachaar.BaseView;
import com.nickelfox.mvp.samachaar.data.repositoriy.model.SamachaarArticle;

import java.util.List;

public interface AllSamachaarContract {

    interface View extends BaseView<Presenter> {

        void showBusinessList(@NonNull List<SamachaarArticle> businessList);

        void showEntertainmentList(@NonNull List<SamachaarArticle> entertainmentList);

        void showHealthList(@NonNull List<SamachaarArticle> healthList);

        void showScienceList(@NonNull List<SamachaarArticle> scienceList);

        void showSportsList(@NonNull List<SamachaarArticle> sportsList);

        void showTechnologyList(@NonNull List<SamachaarArticle> technologyList);

        void showLoading();

        void hideLoading();

        void showError(@NonNull String errorMessage);


    }

    interface Presenter extends BasePresenter {

        void fetchSamachaar();

    }

}
