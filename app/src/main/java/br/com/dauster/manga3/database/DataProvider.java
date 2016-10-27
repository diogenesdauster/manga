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

import static br.com.dauster.manga3.database.DataContract.*;

public class DataProvider extends ContentProvider {

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
    //    private static final SQLiteQueryBuilder sComicQueryBuilder = buildComicQueryBuilder();
//    private static final SQLiteQueryBuilder sSeriesQueryBuilder = buildSeriesQueryBuilder();
//    private static final SQLiteQueryBuilder sStoriesQueryBuilder = buildStoriesQueryBuilder();
    private DataHelper mDataHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;

        matcher.addURI(authority, MangaContract.URI_PATH, MANGA);
        matcher.addURI(authority, MangaContract.URI_PATH + NUMBER, MANGA_ID);
        matcher.addURI(authority, MangaContract.URI_PATH + TEXT, MANGA_SEARCH);
        matcher.addURI(authority, ChapterContract.URI_PATH, CHAPTER);
        matcher.addURI(authority, ChapterContract.URI_PATH + NUMBER, CHAPTER_ID);
        matcher.addURI(authority, ChapterContract.URI_PATH_MANGA + NUMBER, CHAPTER_MANGA);
        matcher.addURI(authority, PageContract.URI_PATH, PAGE);
        matcher.addURI(authority, PageContract.URI_PATH + NUMBER, PAGE_ID);
        matcher.addURI(authority, PageContract.URI_PATH_CHAPTER + NUMBER, PAGE_CHAPTER);
        return matcher;
    }

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

        if (uriType != -1) {
            SQLiteDatabase db = mDataHelper.getWritableDatabase();
            long id = db.insert(getEntityName(uri), null, values);
            db.close();
            // Se der erro na inclusão o id retornado é -1,
            // então levantamos a exceção para ser tratada na tela.
            if (id == -1) {
                throw new RuntimeException("Error inserting moving.");
            }
            notifyChanges(uri);
            return ContentUris.withAppendedId(uri, id);
        } else {
            throw new IllegalArgumentException("Invalid Uri");
        }

    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
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
        notifyChanges(uri);
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
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
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