
package br.com.dauster.manga3.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Manga {

    @SerializedName("mangaId")
    private String mMangaId;
    @SerializedName("artist")
    private String[] mArtist;
    @SerializedName("author")
    private String[] mAuthor;
    @SerializedName("chapters")
    private List<Chapter> mChapters;
    @SerializedName("cover")
    private String mCover;
    @SerializedName("genres")
    private String[] mGenres;
    @SerializedName("href")
    private String mHref;
    @SerializedName("info")
    private String mInfo;
    @SerializedName("lastUpdate")
    private String mLastUpdate;
    @SerializedName("name")
    private String mName;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("yearOfRelease")
    private Long mYearOfRelease;

    public String getArtist() {
        String mRetorno = "";

        for (int i = 0; i < mArtist.length ; i++) {
            mRetorno+= mArtist[i];
            if(i != mArtist.length-1){
                mRetorno+=" , ";
            }
        }
        return mRetorno;
    }

    public void setArtist(String[] artist) {
        mArtist = artist;
    }

    public String getAuthor() {
        String mRetorno = "";

        for (int i = 0; i < mAuthor.length ; i++) {
            mRetorno+= mAuthor[i];
            if(i != mAuthor.length-1){
                mRetorno+=" , ";
            }
        }
        return mRetorno;
    }

    public void setAuthor(String[] author) {
        mAuthor = author;
    }

    public List<Chapter> getChapters() {
        return mChapters;
    }

    public void setChapters(List<Chapter> chapters) {
        mChapters = chapters;
    }

    public String getCover() {
        return mCover;
    }

    public void setCover(String cover) {
        mCover = cover;
    }

    public String getGenres() {
        String mRetorno = "";

        for (int i = 0; i < mGenres.length ; i++) {
            mRetorno+= mGenres[i];
            if(i != mGenres.length-1){
                mRetorno+=" , ";
            }
        }
        return mRetorno;
    }

    public void setGenres(String[] genres) {
        mGenres = genres;
    }

    public String getHref() {
        return mHref;
    }

    public void setHref(String href) {
        mHref = href;
    }

    public String getInfo() {
        return mInfo;
    }

    public void setInfo(String info) {
        mInfo = info;
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

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Long getYearOfRelease() {
        return mYearOfRelease;
    }

    public void setYearOfRelease(Long yearOfRelease) {
        mYearOfRelease = yearOfRelease;
    }

    public String getMangaId() {
        return mMangaId;
    }

    public void setMangaId(String mMangaId) {
        this.mMangaId = mMangaId;
    }

}
