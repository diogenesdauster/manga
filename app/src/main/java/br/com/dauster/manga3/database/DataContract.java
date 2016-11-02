package br.com.dauster.manga3.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import br.com.dauster.manga3.Model.Chapter;
import br.com.dauster.manga3.Model.Manga;
import br.com.dauster.manga3.Model.Page;


public class DataContract {

    // AUTHORITY host para meu banco de dados
    static final String CONTENT_AUTHORITY = "br.com.dauster.manga";
    // Criar a Uri para AUTHORITY ex : content://br.com.dauster.manga
    private static final Uri BASE_CONTENT_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(CONTENT_AUTHORITY).build();

    // Colunas e funcoes da tabela manga
    public static final class MangaContract implements BaseColumns {

        //Colunas
        public static final String ENTITY_NAME = Manga.class.getSimpleName();
        public static final String COLUMN_MANGAID = "mangaid";
        public static final String COLUMN_HREF = "href";
        public static final String COLUMN_ARTIST = "artist";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_COVER = "cover";
        public static final String COLUMN_GENRES = "genres";
        public static final String COLUMN_INFO = "info";
        public static final String COLUMN_LASTUPDATE = "lastupdate";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_YEAROFRELEASE = "yearofrelease";

        // Urls para match
        public static final String URI_PATH      = ENTITY_NAME.toLowerCase();
        public static final String URI_PATH_NAME = COLUMN_NAME;

        // Uris
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(URI_PATH).build();

        public static final Uri CONTENT_URI_NAME =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(URI_PATH_NAME).build();


        // Colunas Query
        public static String[] COLUMNS_LIST_MAIN = new String[]{
                COLUMN_MANGAID,
                COLUMN_NAME,
                COLUMN_COVER
        };

    }

    // Colunas e funcoes da tabela Chapter
    public static final class ChapterContract implements BaseColumns {
        //Colunas
        public static final String ENTITY_NAME = Chapter.class.getSimpleName();
        public static final String COLUMN_CHAPTERID = "chapterid";
        public static final String COLUMN_MANGAID = "mangaid";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LASTUPDATE = "lastupdate";

        // Urls para match
        public static final String URI_PATH = ENTITY_NAME.toLowerCase();
        public static final String URI_PATH_MANGA = COLUMN_MANGAID;

        // Uris
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(URI_PATH).build();

        public static final Uri CONTENT_URI_MANGAID =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(URI_PATH_MANGA).build();


        // Colunas Query
        public static String[] COLUMNS_LIST_MAIN = new String[]{
                COLUMN_CHAPTERID,
                COLUMN_MANGAID,
                COLUMN_NAME,
                COLUMN_LASTUPDATE
        };



    }

    public static final class PageContract implements BaseColumns {
        //Colunas
        public static final String ENTITY_NAME = Page.class.getSimpleName();
        public static final String COLUMN_PAGEID = "pageid";
        public static final String COLUMN_CHAPTERID = "chapterid";
        public static final String COLUMN_URL = "url";

        // Urls para match
        public static final String URI_PATH = ENTITY_NAME.toLowerCase();
        public static final String URI_PATH_CHAPTER = COLUMN_CHAPTERID;

        // Uris
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(URI_PATH).build();

        public static final Uri CONTENT_URI_CHAPTERID =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(URI_PATH_CHAPTER).build();

        // Colunas Query
        public static String[] COLUMNS_LIST_MAIN = new String[]{
                COLUMN_CHAPTERID,
                COLUMN_CHAPTERID,
                COLUMN_URL,
        };


    }

    public static Uri buildUri(Uri uri,String value) {
        return  uri.buildUpon().appendEncodedPath(value).build();
    }


}