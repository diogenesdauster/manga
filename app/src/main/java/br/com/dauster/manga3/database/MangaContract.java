package br.com.dauster.manga3.database;

/**
 * Created by dauster on 25/10/16.
 */

public interface MangaContract {
    // Nome da tabela no banco de dados
    String TABLE_NAME = "Mangas";

    // Colunas do banco de dados
    String COL_MANGA_ID      = "mangaid";
    String COL_ARTIST        = "artist";
    String COL_AUTHOR        = "author";
    String COL_COVER         = "cover";
    String COL_GENRES        = "genres";
    String COL_HREF          = "href";
    String COL_INFO          = "info";
    String COL_LASTUPDATE    = "lastupdate";
    String COL_NAME          = "name";
    String COL_STATUS        = "status";
    String COL_YEAROFRELEASE = "yearofrelease";

    // Colunas utilizadas pelo adapter do fragment de favoritos
//    String[] LIST_COLUMNS = new String[]{
//            MovieContract._ID,
//            MovieContract.COL_IMDB_ID,
//            MovieContract.COL_TITLE,
//            MovieContract.COL_POSTER,
//            MovieContract.COL_YEAR
//    };

}


//    private String mMangaId;
//    @SerializedName("artist")
//    private String[] mArtist;
//    @SerializedName("author")
//    private String[] mAuthor;
//    @SerializedName("chapters")
//    private List<Chapter> mChapters;
//    @SerializedName("cover")
//    private String mCover;
//    @SerializedName("genres")
//    private String[] mGenres;
//    @SerializedName("href")
//    private String mHref;
//    @SerializedName("info")
//    private String mInfo;
//    @SerializedName("lastUpdate")
//    private String mLastUpdate;
//    @SerializedName("name")
//    private String mName;
//    @SerializedName("status")
//    private String mStatus;
//    @SerializedName("yearOfRelease")
//    private Long mYearOfRelease;