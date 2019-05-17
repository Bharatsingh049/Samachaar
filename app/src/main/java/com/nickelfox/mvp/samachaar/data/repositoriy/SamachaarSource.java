package com.nickelfox.mvp.samachaar.data.repositoriy;

import androidx.annotation.NonNull;

import com.nickelfox.mvp.samachaar.data.repositoriy.model.SamachaarArticle;

import java.util.List;

public interface SamachaarSource {

    //Todo Delete this interface if it is not used by both repositories
    interface LoadSamachaarCallBack{
  void onSamachaarLoaded(@NonNull List<SamachaarArticle> list,String category);

    }
}
