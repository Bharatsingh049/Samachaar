package com.nickelfox.mvp.samachaar.data.repositoriy;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.nickelfox.mvp.samachaar.data.repositoriy.local.SamachaarLocalRepository;
import com.nickelfox.mvp.samachaar.data.repositoriy.model.SamachaarArticle;
import com.nickelfox.mvp.samachaar.data.repositoriy.remote.SamachaarRemoteRepository;

import java.util.List;

public class SamachaarRepository {

    private final String[] categories = {"business", "entertainment", "health", "science", "sports", "technology"};

    private static SamachaarRepository INSTANCE;

    private final SamachaarLocalRepository samachaarLocalRepository;

    private final SamachaarRemoteRepository samachaarRemoteRepository;

    private LiveData<List<SamachaarArticle>> samachaarLiveDataList;

    private MutableLiveData<List<SamachaarArticle>> mutableLiveDataList;
    private SamachaarRepository(@NonNull SamachaarLocalRepository samachaarLocalRepository
            , @NonNull SamachaarRemoteRepository samachaarRemoteRepository) {
        this.samachaarLocalRepository = samachaarLocalRepository;
        this.samachaarRemoteRepository = samachaarRemoteRepository;
        mutableLiveDataList = samachaarRemoteRepository.getAllSamachaar();
        fetchSamachaar();
        this.samachaarLiveDataList = samachaarLocalRepository.getAllSamachaar();
    }


    public static SamachaarRepository getInstance(@NonNull SamachaarLocalRepository samachaarLocalRepository
            , @NonNull SamachaarRemoteRepository samachaarRemoteRepository) {
        if (INSTANCE == null) {
            INSTANCE = new SamachaarRepository(samachaarLocalRepository, samachaarRemoteRepository);
        }
        return INSTANCE;
    }

    private LiveData<List<SamachaarArticle>> listLiveData = Transformations.switchMap(mutableLiveDataList, new Function<List<SamachaarArticle>, LiveData<List<SamachaarArticle>>>() {
        @Override
        public LiveData<List<SamachaarArticle>> apply(List<SamachaarArticle> input) {
            samachaarLocalRepository.insertSamachaar(input);
            return null;
        }
    });

    public LiveData<List<SamachaarArticle>> getAllSamachaarFromRemote() {
        return listLiveData;
    }

    public LiveData<List<SamachaarArticle>> getSamachaarByCategory(String category){
        return samachaarLocalRepository.getSamchaarByCategory(category);
    }

    private void fetchSamachaar() {
        samachaarLocalRepository.deleteAllSamachaar(new SamachaarLocalRepository.AsyncDeleteResponseCallBack() {
            @Override
            public void onDeleted(int output) {
                if (output >= 0) {

                    for (int i = 0; i < 6; i++) {
                        samachaarRemoteRepository.getSamachaarList(categories[i], "In", "en");
                    }

                }
            }
        });


    }


}
