package br.com.dauster.manga3.Loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import br.com.dauster.manga3.Http.MangaHttp;
import br.com.dauster.manga3.Model.Manga;


public class MangaSearchTask extends AsyncTaskLoader<List<Manga>> {

    List<Manga> mangas;
    String query;


    public MangaSearchTask(Context context,String query) {
        super(context);
        this.query = query;
        this.mangas = new ArrayList<>();

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (query != null) {
            forceLoad();
        } else {
            deliverResult(mangas);
        }
    }

    @Override
    public List<Manga> loadInBackground() {
        if(query == "ALL") {
            mangas.addAll(MangaHttp.searchMangas());
        }else{
            mangas.addAll(MangaHttp.searchManga(query));
        }
        query = null;
        return mangas;
    }
}