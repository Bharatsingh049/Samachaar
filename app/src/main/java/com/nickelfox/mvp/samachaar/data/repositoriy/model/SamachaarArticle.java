
package com.nickelfox.mvp.samachaar.data.repositoriy.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Samachaar_articles")
public class SamachaarArticle {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "samachaarid")
    private int id;

    @NonNull
    @ColumnInfo(name = "category")
    private String category;

    @NonNull
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;

    @NonNull
    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    private String description;

    @NonNull
    @ColumnInfo(name = "url")
    @SerializedName("url")
    @Expose
    private String url;

    @NonNull
    @ColumnInfo(name = "urltoimage")
    @SerializedName("urlToImage")
    @Expose
    private String urlToImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SamachaarArticle(@NonNull String title, @NonNull String description, @NonNull String url, @NonNull String urlToImage) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
    }

    public SamachaarArticle() {

    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }



}
