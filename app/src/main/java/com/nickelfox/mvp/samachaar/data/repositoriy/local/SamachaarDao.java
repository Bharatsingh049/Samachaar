package com.nickelfox.mvp.samachaar.data.repositoriy.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nickelfox.mvp.samachaar.data.repositoriy.model.SamachaarArticle;

import java.util.List;

@Dao
public interface SamachaarDao {

    @Query("SELECT * FROM Samachaar_articles")
    LiveData<List<SamachaarArticle>> getAllSamachaar();

    @Query("SELECT * FROM SAMACHAAR_ARTICLES WHERE category = :category")
    LiveData<List<SamachaarArticle>> getSamachaarByCategory(String category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSamachaar(List<SamachaarArticle> samachaarArticles);

    @Query("DELETE FROM samachaar_articles")
    int deleteAllSamachaar();

    @Query("SELECT COUNT(*) FROM samachaar_articles")
    long countSamachaar();

}
