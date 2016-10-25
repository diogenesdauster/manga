package br.com.dauster.manga3.database;

/**
 * Created by dauster on 25/10/16.
 */

public interface Chapter {
    String TABLE_NAME = "Chapter";

    // Colunas do banco de dados
    String COL_MANGA_ID      = "chapterId";
    String COL_ARTIST        = "name";
    String COL_AUTHOR        = "author";
    String COL_COVER         = "cover";
    String COL_GENRES        = "genres";
    String COL_HREF          = "href";
    String COL_INFO          = "info";
    String COL_LASTUPDATE    = "lastupdate";
    String COL_NAME          = "name";
    String COL_STATUS        = "status";
    String COL_YEAROFRELEASE = "yearofrelease";

}

//    @SerializedName("chapterId")
//    private Long mChapterId;
//    @SerializedName("name")
//    private String mName;
