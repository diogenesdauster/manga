package br.com.dauster.manga3.database;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import br.com.dauster.manga3.Model.Chapter;
import br.com.dauster.manga3.Model.Manga;
import br.com.dauster.manga3.Model.Page;

public class DataUtil {


    public static String listToString(List<String> lista){
        String result = "";
        for (String value: lista) {
            result +=value +" ,";
        }

        return result.substring(0,result.length()-1);
    }


    public static int handleListManga(ContentResolver contentResolver, List<Manga> mangas) {

        List<ContentValues> contentValuesList = new ArrayList<>();

        for (Manga manga : mangas) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataContract.MangaContract.COLUMN_HREF, manga.getHref());
            contentValues.put(DataContract.MangaContract.COLUMN_NAME, manga.getName());
            contentValues.put(DataContract.MangaContract.COLUMN_COVER, manga.getCover());
            contentValuesList.add(contentValues);
        }


        ContentValues[] mangasValues = new ContentValues[contentValuesList.size()];
        int result = contentResolver.bulkInsert(DataContract.MangaContract.CONTENT_URI,
                contentValuesList.toArray(mangasValues));

        return result;

    }

    public static int handleListChapter(ContentResolver contentResolver,List<Chapter> chapters,
                                        String mangaId ) {

        List<ContentValues> contentValuesList = new ArrayList<>();

        for (Chapter chapter : chapters) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataContract.ChapterContract.COLUMN_CHAPTERID, chapter.getChapterId());
            contentValues.put(DataContract.ChapterContract.COLUMN_NAME, chapter.getName());
            contentValues.put(DataContract.ChapterContract.COLUMN_MANGAID, mangaId);
            contentValuesList.add(contentValues);
        }


        ContentValues[] chaptersValues = new ContentValues[contentValuesList.size()];
        int result = contentResolver.bulkInsert(DataContract.ChapterContract.CONTENT_URI,
                contentValuesList.toArray(chaptersValues));

        return result;

    }


    public static int handleListPages(ContentResolver contentResolver,List<Page> pages,
                                        Long chapterId ) {

        List<ContentValues> contentValuesList = new ArrayList<>();

        for (Page page : pages) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataContract.PageContract.COLUMN_PAGEID   , page.getPageId());
            contentValues.put(DataContract.PageContract.COLUMN_CHAPTERID, chapterId);
            contentValues.put(DataContract.PageContract.COLUMN_URL      , page.getUrl());
            contentValuesList.add(contentValues);
        }


        ContentValues[] pagesValues = new ContentValues[contentValuesList.size()];
        int result = contentResolver.bulkInsert(DataContract.PageContract.CONTENT_URI,
                contentValuesList.toArray(pagesValues));

        return result;

    }

    public static Uri handleManga(ContentResolver contentResolver, Manga manga) {


        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.MangaContract.COLUMN_AUTHOR,listToString(manga.getAuthor()));
        contentValues.put(DataContract.MangaContract.COLUMN_ARTIST,listToString(manga.getArtist()));
        contentValues.put(DataContract.MangaContract.COLUMN_COVER,manga.getCover());
        contentValues.put(DataContract.MangaContract.COLUMN_NAME, manga.getName());
        contentValues.put(DataContract.MangaContract.COLUMN_GENRES,listToString(manga.getGenres()));
        contentValues.put(DataContract.MangaContract.COLUMN_HREF, manga.getHref());
        contentValues.put(DataContract.MangaContract.COLUMN_INFO,manga.getInfo());
        contentValues.put(DataContract.MangaContract.COLUMN_LASTUPDATE,manga.getLastUpdate());
        contentValues.put(DataContract.MangaContract.COLUMN_STATUS,manga.getStatus());
        contentValues.put(DataContract.MangaContract.COLUMN_YEAROFRELEASE,manga.getYearOfRelease());


        Uri result = contentResolver.insert(DataContract.MangaContract.CONTENT_URI,
                contentValues);

        handleListChapter(contentResolver,manga.getChapters(),manga.getHref());

        return result;

    }


    public static Uri handleChapter(ContentResolver contentResolver, Chapter chapter) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.ChapterContract.COLUMN_CHAPTERID ,chapter.getChapterId());
        contentValues.put(DataContract.ChapterContract.COLUMN_MANGAID   ,chapter.getHref());
        contentValues.put(DataContract.ChapterContract.COLUMN_NAME      ,chapter.getName());
        contentValues.put(DataContract.ChapterContract.COLUMN_LASTUPDATE,chapter.getLastUpdate());


        Uri result = contentResolver.insert(DataContract.ChapterContract.CONTENT_URI,
                contentValues);

        handleListPages(contentResolver,chapter.getPages(),chapter.getChapterId());

        return result;

    }


    public static Uri handlePage(ContentResolver contentResolver, Page page) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.PageContract.COLUMN_PAGEID   ,page.getPageId());
        contentValues.put(DataContract.PageContract.COLUMN_CHAPTERID,page.getChapterId());
        contentValues.put(DataContract.PageContract.COLUMN_URL      ,page.getUrl());

        Uri result = contentResolver.insert(DataContract.PageContract.CONTENT_URI,
                contentValues);

        return result;

    }


    public static Boolean isSaveManga(ContentResolver contentResolver, String mangaId){

        Cursor cursor = contentResolver.query(
                DataContract.buildUri(DataContract.MangaContract.CONTENT_URI,mangaId),
                new String[]{ DataContract.MangaContract.COLUMN_INFO },
                null,
                null,
                null
        );
        boolean isSave = false;
        if (cursor != null) {
            isSave = !cursor.getString(cursor.getColumnIndex(
                    DataContract.MangaContract.COLUMN_INFO)).isEmpty();
            cursor.close();
        }
        return isSave;
    }


    public static Boolean isSaveChapter(ContentResolver contentResolver, Long chapterId){

        Cursor cursor = contentResolver.query(
                ContentUris.appendId(DataContract.ChapterContract.CONTENT_URI.buildUpon(),chapterId).build(),
                new String[]{ DataContract.ChapterContract.COLUMN_LASTUPDATE },
                null,
                null,
                null
        );
        boolean isSave = false;
        if (cursor != null) {
            isSave = !cursor.getString(cursor.getColumnIndex(
                    DataContract.MangaContract.COLUMN_LASTUPDATE)).isEmpty();
            cursor.close();
        }
        return isSave;
    }

//    public static Boolean isSavePage(ContentResolver contentResolver, Long pageId){
//        Cursor cursor = contentResolver.query(
//                ContentUris.appendId(DataContract.PageContract.CONTENT_URI.buildUpon(),pageId).build(),
//                new String[]{ DataContract.PageContract.COLUMN_URL },
//                null,
//                null,
//                null
//        );
//        boolean isSave = false;
//        if (cursor != null) {
//            isSave = !cursor.getString(cursor.getColumnIndex(
//                    DataContract.PageContract.COLUMN_URL)).isEmpty();
//            cursor.close();
//        }
//        return isSave;
//    }


}
