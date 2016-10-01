
package br.com.dauster.manga3.Model;

import com.google.gson.annotations.SerializedName;


public class Chapter {

    @SerializedName("chapterId")
    private Long mChapterId;
    @SerializedName("name")
    private String mName;

    public Long getChapterId() {
        return mChapterId;
    }

    public void setChapterId(Long chapterId) {
        mChapterId = chapterId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
