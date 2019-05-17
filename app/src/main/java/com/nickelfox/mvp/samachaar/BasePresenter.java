package com.nickelfox.mvp.samachaar;


import com.nickelfox.mvp.samachaar.allsamachaar.AllSamachaarContract;

public interface BasePresenter {

    void start(AllSamachaarContract.View view);

    void onDestroy();
}
