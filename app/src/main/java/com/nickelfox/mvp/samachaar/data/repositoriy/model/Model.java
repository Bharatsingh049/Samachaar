
package com.nickelfox.mvp.samachaar.data.repositoriy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class Model {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;

    @SerializedName("articles")
    @Expose
    private List<SamachaarModel> samachaarList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<SamachaarModel> getSamachaarList() {
        return samachaarList;
    }

    public void setSamachaarList(List<SamachaarModel> samachaarList) {
        this.samachaarList = samachaarList;
    }

}
