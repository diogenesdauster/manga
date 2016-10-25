
package br.com.dauster.manga3.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChapterPages {

    @SerializedName("href")
    private String mHref;
    @SerializedName("lastUpdate")
    private String mLastUpdate;
    @SerializedName("name")
    private String mName;
    @SerializedName("pages")
    private List<Page> mPages;

    public String getHref() {
        return mHref;
    }

    public void setHref(String href) {
        mHref = href;
    }

    public String getLastUpdate() {
        return mLastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        mLastUpdate = lastUpdate;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Page> getPages() {
        return mPages;
    }

    public void setPages(List<Page> pages) {
        mPages = pages;
    }

}
