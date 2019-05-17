package com.nickelfox.mvp.samachaar.data.repositoriy.local;


import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.nickelfox.mvp.samachaar.data.repositoriy.model.SamachaarArticle;

import java.util.List;

@SuppressWarnings("ALL")
public class SamachaarLocalRepository {

    private SamachaarDao samachaarDao;

    private LiveData<List<SamachaarArticle>> samachaarList;

    private static SamachaarLocalRepository INSTANCE;

    private SamachaarLocalRepository(@NonNull SamachaarDao samachaarDao) {
        this.samachaarDao = samachaarDao;
        this.samachaarList =this.samachaarDao.getAllSamachaar();

    }

    public static SamachaarLocalRepository getInstance(@NonNull SamachaarDao samachaarDao) {
        if (INSTANCE == null) {
            INSTANCE = new SamachaarLocalRepository(samachaarDao);
        }
        return INSTANCE;
    }

    /**/

    public interface AsyncCountResponseCallBack {
        void onCounted(long output);
    }

    public interface AsyncDeleteResponseCallBack {
        void onDeleted(int output);
    }

    public LiveData<List<SamachaarArticle>> getAllSamachaar(){return samachaarList;}

    public void insertSamachaar(List<SamachaarArticle> list){
      new insertSamachaarAsyncTask(samachaarDao).execute(list);
    }

    public void deleteAllSamachaar(@NonNull AsyncDeleteResponseCallBack callBack){
        new deleteSamachaarAsyncTask(samachaarDao,callBack).execute();
    }


    public void countSamachaar(@NonNull AsyncCountResponseCallBack callBack){
        new CountSamachaarAsyncTask(samachaarDao,callBack).execute();
    }


    private static class insertSamachaarAsyncTask extends AsyncTask<List<SamachaarArticle>,Void,Void>{

        private SamachaarDao asyncTaskDao;

        insertSamachaarAsyncTask(SamachaarDao samachaarDao){
            this.asyncTaskDao =samachaarDao;
        }


        @Override
        protected Void doInBackground(List<SamachaarArticle>... lists) {

            asyncTaskDao.insertSamachaar(lists[0]);
            return null;
        }
    }


    private static class deleteSamachaarAsyncTask extends AsyncTask<Void,Void,Integer>{

        private SamachaarDao asyncTaskDao;

        private AsyncDeleteResponseCallBack callback = null;

        deleteSamachaarAsyncTask(SamachaarDao samachaarDao,@NonNull AsyncDeleteResponseCallBack callBack){
            this.asyncTaskDao =samachaarDao;
            this.callback=callBack;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
           Integer deletedRows = asyncTaskDao.deleteAllSamachaar();
            return deletedRows;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            callback.onDeleted(integer);
        }
    }

    private static class CountSamachaarAsyncTask extends AsyncTask<Void,Void,Long>{

        private SamachaarDao asyncTaskDao;



        private AsyncCountResponseCallBack callback = null;

        CountSamachaarAsyncTask(SamachaarDao samachaarDao,@NonNull AsyncCountResponseCallBack callBack){
            this.asyncTaskDao =samachaarDao;
            this.callback = callBack;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            long count = asyncTaskDao.countSamachaar();
            return count;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            callback.onCounted(aLong);
        }


    }



}
