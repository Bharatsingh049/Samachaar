package com.nickelfox.mvp.samachaar.data.repositoriy.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nickelfox.mvp.samachaar.data.repositoriy.SamachaarRepository;
import com.nickelfox.mvp.samachaar.data.repositoriy.model.SamachaarArticle;

import java.util.ArrayList;
import java.util.List;

import static com.nickelfox.mvp.samachaar.data.repositoriy.local.SamachaarDatabase.DATABASE_VERSION;

@Database(entities = {SamachaarArticle.class}, version = DATABASE_VERSION)
public abstract class SamachaarDatabase extends RoomDatabase {

    static String DATABASE_NAME = "Samachaar.db";

    static final int DATABASE_VERSION = 1;

    private static SamachaarDatabase INSTANCE;

    private List<SamachaarArticle> samachaarArticleList;

    public abstract SamachaarDao samachaarDao();

    public static synchronized SamachaarDatabase getInstance(Context context) {

        if (INSTANCE == null) {


            INSTANCE = Room.databaseBuilder(context
                    , SamachaarDatabase.class
                    , DATABASE_NAME)
                    .build();
            /*if (INSTANCE != null) {
                INSTANCE.samachaarArticleList=new ArrayList<>();
                INSTANCE.samachaarArticleList.add(new SamachaarArticle("Title","Description","Url","UrlToImage"));
                INSTANCE.samachaarDao().insertSamachaar(INSTANCE.samachaarArticleList);
            }*/

        }
        return INSTANCE;
    }
}
