
package br.com.dauster.manga3.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Page implements Serializable {

    private Long mChapterId;
    @SerializedName("pageId")
    private Long mPageId;
    @SerializedName("url")
    private String mUrl;


    public Long getChapterId() {
        return mChapterId;
    }

    public void setChapterId(Long mChapterId) {
        this.mChapterId = mChapterId;
    }

    public Long getPageId() {
        return mPageId;
    }

    public void setPageId(Long mPageId) {
        this.mPageId = mPageId;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
