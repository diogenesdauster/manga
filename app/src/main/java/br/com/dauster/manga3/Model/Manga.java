
package br.com.dauster.manga3.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Manga implements Serializable {

    @SerializedName("mangaId")
    private String mMangaId;
    @SerializedName("artist")
    private List<String> mArtist;
    @SerializedName("author")
    private List<String> mAuthor;
    @SerializedName("chapters")
    private List<Chapter> mChapters;
    @SerializedName("cover")
    private String mCover;
    @SerializedName("genres")
    private List<String> mGenres;
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

    public List<String> getArtist() {
        return mArtist;
    }

    public void setArtist(List<String> artist) {
        mArtist = artist;
    }

    public List<String> getAuthor() {
        return mAuthor;
    }

    public void setAuthor(List<String> author) {
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

    public List<String> getGenres() {
        return mGenres;
    }

    public void setGenres(List<String> genres) {
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

    public void setMangaId(String mangaId) {
        mMangaId = mangaId;
    }

}
