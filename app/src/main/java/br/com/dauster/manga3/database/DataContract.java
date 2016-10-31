package br.com.dauster.manga3.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import br.com.dauster.manga3.Model.Chapter;
import br.com.dauster.manga3.Model.Manga;
import br.com.dauster.manga3.Model.Page;


public class DataContract {

    static final String CONTENT_AUTHORITY = "br.com.dauster.manga";
    private static final Uri BASE_CONTENT_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(CONTENT_AUTHORITY).build();

    public static final class MangaContract implements BaseColumns {

        public static final String ENTITY_NAME = Manga.class.getSimpleName();
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
        public static final String URI_PATH      = ENTITY_NAME.toLowerCase();
        public static final String URI_PATH_NAME = URI_PATH +"/"+COLUMN_NAME;

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(URI_PATH).build();

        public static final Uri CONTENT_URI_NAME =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(URI_PATH).
                        appendEncodedPath(COLUMN_NAME).build();


        public static Uri buildUri(Uri uri,String value) {
            return  uri.buildUpon().appendEncodedPath(value).build();
        }


        // Colunas utilizadas pelo adapter do fragment de favoritos
        public static String[] COLUMNS_LIST_MAIN = new String[]{
                COLUMN_HREF,
                COLUMN_NAME,
                COLUMN_COVER
        };

    }


    public static final class ChapterContract implements BaseColumns {

        public static final String ENTITY_NAME = Chapter.class.getSimpleName();
        public static final String COLUMN_CHAPTERID = "chapterid";
        public static final String COLUMN_MANGAID = "mangaid";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LASTUPDATE = "lastupdate";
        public static final String URI_PATH = ENTITY_NAME.toLowerCase();
        public static final String URI_PATH_MANGA = URI_PATH + "/" + COLUMN_MANGAID;
    }

    public static final class PageContract implements BaseColumns {

        public static final String ENTITY_NAME = Page.class.getSimpleName();
        public static final String COLUMN_PAGEID = "pageid";
        public static final String COLUMN_CHAPTERID = "chapterid";
        public static final String COLUMN_URL = "url";
        public static final String URI_PATH = ENTITY_NAME.toLowerCase();
        public static final String URI_PATH_CHAPTER = URI_PATH + "/" + COLUMN_CHAPTERID;

    }

}