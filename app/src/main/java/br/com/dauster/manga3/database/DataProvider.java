package br.com.dauster.manga3.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import br.com.dauster.manga3.database.DataContract.ChapterContract;
import br.com.dauster.manga3.database.DataContract.MangaContract;
import br.com.dauster.manga3.database.DataContract.PageContract;

import static br.com.dauster.manga3.database.DataContract.CONTENT_AUTHORITY;

public class DataProvider extends ContentProvider {


    // CONSTANTE PARA MONTAR O PROVIDER
    // BOAS PRATICAS SEMPRE DIVIDIR POR FAIXAS
    // EX: 100 , 200 , 300
    private static final String NUMBER = "/#";
    private static final String TEXT = "/*";

    private static final int MANGA = 100;
    private static final int MANGA_ID = 101;
    private static final int MANGA_SEARCH = 102;

    private static final int CHAPTER = 200;
    private static final int CHAPTER_ID = 201;
    private static final int CHAPTER_MANGA = 202;

    private static final int PAGE = 300;
    private static final int PAGE_ID = 301;
    private static final int PAGE_CHAPTER = 302;

    private static final UriMatcher mUriMatcher = buildUriMatcher();
    private DataHelper mDataHelper;

    // Criando o Matcher do ContentProvider
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;

        // Informa ao ContentProvider que ele só aceitar essas Urls
        matcher.addURI(authority, MangaContract.URI_PATH, MANGA);
        matcher.addURI(authority, MangaContract.URI_PATH + TEXT, MANGA_ID);
        matcher.addURI(authority, MangaContract.URI_PATH_NAME + TEXT, MANGA_SEARCH);

        matcher.addURI(authority, ChapterContract.URI_PATH, CHAPTER);
        matcher.addURI(authority, ChapterContract.URI_PATH + NUMBER + TEXT, CHAPTER_ID);
        matcher.addURI(authority, ChapterContract.URI_PATH_MANGA + TEXT, CHAPTER_MANGA);

        matcher.addURI(authority, PageContract.URI_PATH, PAGE);
        matcher.addURI(authority, PageContract.URI_PATH + NUMBER, PAGE_ID);
        matcher.addURI(authority, PageContract.URI_PATH_CHAPTER + NUMBER, PAGE_CHAPTER);
        return matcher;
    }

//TODO : implementar relacionamentos testar depois
//    private static sqlitequerybuilder buildmangaquerybuilder() {
//        sqlitequerybuilder querybuilder = new sqlitequerybuilder();
//        querybuilder.setdistinct(true);
//        querybuilder.settables(mangacontract.entity_name +
//                " left join " + chaptercontract.entity_name +
//                " on " + mangacontract.column_href +
//                " = " + chaptercontract.column_mangaid
//        );
//        map<string, string> projectionmap = new hashmap<>();
//        projectionmap.put(mangacontract.column_href,mangacontract.column_href );
//        projectionmap.put(mangacontract.column_artist,mangacontract.column_artist);
//        projectionmap.put(mangacontract.column_author,mangacontract.column_author);
//        projectionmap.put(mangacontract.column_cover,mangacontract.column_cover);
//        projectionmap.put(mangacontract.column_genres,mangacontract.column_genres);
//        projectionmap.put(mangacontract.column_info,mangacontract.column_info);
//        projectionmap.put(mangacontract.column_lastupdate,mangacontract.column_lastupdate);
//        projectionmap.put(mangacontract.column_name,mangacontract.column_name);
//        projectionmap.put(mangacontract.column_status,mangacontract.column_status);
//        projectionmap.put(mangacontract.column_yearofrelease, mangacontract.column_yearofrelease);
//        projectionmap.put(chaptercontract.column_chapterid, chaptercontract.column_chapterid);
//        projectionmap.put(chaptercontract.column_mangaid, chaptercontract.column_mangaid);
//        projectionmap.put(chaptercontract.column_name, chaptercontract.column_name);
//        projectionmap.put(chaptercontract.column_lastupdate, chaptercontract.column_lastupdate);
//        querybuilder.setprojectionmap(projectionmap);
//        return querybuilder;
//    }
//    private static sqlitequerybuilder buildchapterquerybuilder() {
//        sqlitequerybuilder querybuilder = new sqlitequerybuilder();
//        querybuilder.setdistinct(true);
//        querybuilder.settables(chaptercontract.entity_name +
//                " left join " + pagecontract.entity_name +
//                " on " + pagecontract.column_chapterid +
//                " = " +  chaptercontract.column_chapterid
//        );
//        map<string, string> projectionmap = new hashmap<>();
//
//        projectionmap.put(chaptercontract.column_chapterid, chaptercontract.column_chapterid);
//        projectionmap.put(chaptercontract.column_mangaid, chaptercontract.column_mangaid);
//        projectionmap.put(chaptercontract.column_name, chaptercontract.column_name);
//        projectionmap.put(chaptercontract.column_lastupdate, chaptercontract.column_lastupdate);
//        projectionmap.put(pagecontract.column_chapterid, pagecontract.column_chapterid);
//        projectionmap.put(pagecontract.column_pageid, pagecontract.column_pageid);
//        projectionmap.put(pagecontract.column_url, pagecontract.column_url);
//
//        querybuilder.setprojectionmap(projectionmap);
//        return querybuilder;
//    }

    public DataProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //De acordo com URI verifica se o retorna sera de uma linha ou de varias linhas
    //isso por que possa habilitar a chamada de varias threads de acordo com retorno
    @Override
    public String getType(Uri uri) {
        int uriType = mUriMatcher.match(uri);
        // CURSOR_DIR_BASE_TYPE  : Informamos ao android que vamos retornar vários registros
        // CURSOR_ITEM_BASE_TYPE : Informamos ao android que vamos retornar um único registro
        switch (uriType) {
            case MANGA:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY;
            case MANGA_ID:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY;
            case MANGA_SEARCH:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY;
            case CHAPTER:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY;
            case CHAPTER_ID:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY;
            case CHAPTER_MANGA:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY;
            case PAGE:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY;
            case PAGE_ID:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY;
            case PAGE_CHAPTER:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY;
            default:
                throw new IllegalArgumentException("Invalid Uri");
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = mUriMatcher.match(uri);

        // Verifica se a Uri de maatch
        if (uriType != -1) {
            SQLiteDatabase db = mDataHelper.getWritableDatabase(); // abre conexão com o banco
            long id = db.insert(getEntityName(uri), null, values); // inclui o registro
            db.close(); // fecha a conexão
            // Se der erro na inclusão o id retornado é -1,
            // então levantamos a exceção para ser tratada na tela.
            if (id == -1) {
                throw new RuntimeException("Error inserting moving.");
            }
            // Caso a operação no banco ocorra sem problemas, notificamos a Uri
            // para que a listagem de favoritos seja atualizada.
            notifyChanges(uri);
            return ContentUris.withAppendedId(uri, id); // retorna o caminho do novo registro incluido
        } else {
            throw new IllegalArgumentException("Invalid Uri");
        }

    }


    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        String entityName = getEntityName(uri);
        SQLiteDatabase db = mDataHelper.getWritableDatabase();
        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {
                long id = db.insert(entityName, null, value);
                if (id > 0) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        //noinspection ConstantConditions
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
    }



    @Override
    public boolean onCreate() {
        // Criado a instacia do Banco de Dados
        mDataHelper = new DataHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        int uriType = mUriMatcher.match(uri);
        SQLiteDatabase db = mDataHelper.getReadableDatabase();
        Cursor cursor;

        switch (uriType) {

            case MANGA:
                cursor = db.query(MangaContract.ENTITY_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MANGA_ID:
                String queryMangaId = uri.getLastPathSegment();
                cursor = db.query(MangaContract.ENTITY_NAME,
                        projection, MangaContract.COLUMN_NAME + " = ?", new String[]{queryMangaId}
                        , null, null,
                        sortOrder);
                break;
            case MANGA_SEARCH:
                String queryMangaSearch = uri.getLastPathSegment();
                cursor = db.query(MangaContract.ENTITY_NAME,
                        projection, MangaContract.COLUMN_NAME + " LIKE ?",
                        new String[]{"%"+ queryMangaSearch + "%"}
                        , null, null,
                        sortOrder);
                break;
            case CHAPTER_ID:
                String queryChapterId = uri.getLastPathSegment();
                cursor = db.query(ChapterContract.ENTITY_NAME,
                        projection, ChapterContract.COLUMN_CHAPTERID + " = ?",
                        new String[]{queryChapterId}
                        , null, null,
                        sortOrder);
                break;
            case CHAPTER_MANGA:
                String queryChapterMangaId = uri.getLastPathSegment();
                cursor = db.query(ChapterContract.ENTITY_NAME,
                        projection, ChapterContract.COLUMN_MANGAID + " = ?",
                        new String[]{queryChapterMangaId}
                        , null, null,
                        sortOrder);
                break;
            case PAGE_ID:
                String queryPageId = uri.getLastPathSegment();
                cursor = db.query(PageContract.ENTITY_NAME,
                        projection, PageContract.COLUMN_PAGEID + " = ?",
                        new String[]{queryPageId}
                        , null, null,
                        sortOrder);
                break;
            case PAGE_CHAPTER:
                String queryPageChapterId = uri.getLastPathSegment();
                //uri.getPathSegments
                cursor = db.query(ChapterContract.ENTITY_NAME,
                        projection, ChapterContract.COLUMN_CHAPTERID + " = ?",
                        new String[]{queryPageChapterId}
                        , null, null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Invalid Uri");


        }

        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;


    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void notifyChanges(Uri uri) {
        // Caso a operação no banco ocorra sem problemas, notificamos a Uri
        // para que a listagem de favoritos seja atualizada.
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }

    private String getEntityName(@NonNull Uri uri) {
        String entityName;
        switch (mUriMatcher.match(uri)) {
            case MANGA:
                entityName = MangaContract.ENTITY_NAME;
                break;
            case CHAPTER:
                entityName = ChapterContract.ENTITY_NAME;
                break;
            case PAGE:
                entityName = PageContract.ENTITY_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
        return entityName;
    }

}
