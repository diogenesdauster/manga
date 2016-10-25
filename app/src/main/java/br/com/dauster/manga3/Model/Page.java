
package br.com.dauster.manga3.Model;

import com.google.gson.annotations.SerializedName;

public class Page {

    @SerializedName("pageId")
    private Long mPageId;
    @SerializedName("url")
    private String mUrl;

    public Long getPageId() {
        return mPageId;
    }

    public void setPageId(Long pageId) {
        mPageId = pageId;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
