package br.com.dauster.manga3.Loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import br.com.dauster.manga3.Http.MangaHttp;
import br.com.dauster.manga3.Model.Manga;
import br.com.dauster.manga3.database.DataUtil;


public class MangaSearchById extends AsyncTaskLoader<Manga> {

    Manga  mManga;
    String mMangaId;

    public MangaSearchById(Context context,String id) {
        super(context);
        this.mMangaId = id;
        this.mManga = new Manga();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(mMangaId != null){
            forceLoad();
        }else{
            deliverResult(mManga);
        }
    }

    @Override
    public Manga loadInBackground() {
        mManga = MangaHttp.searchMangaById(mMangaId);
        if (mManga != null) {
            DataUtil.handleManga(getContext().getContentResolver(), mManga);
        }
        mMangaId = null;
        return mManga;
    }
}
