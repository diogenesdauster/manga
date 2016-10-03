package br.com.dauster.manga3.Loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import br.com.dauster.manga3.Http.MangaHttp;
import br.com.dauster.manga3.Model.Manga;


public class MangaSearchTask extends AsyncTaskLoader<List<Manga>> {

    List<Manga> mangas;
    String args;


    public MangaSearchTask(Context context,String args,List<Manga> mangas) {
        super(context);
        this.args = args;
        this.mangas = new ArrayList<>();
        if(mangas != null){
            this.mangas.addAll(mangas);
        }

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (args != null) {
            forceLoad();
        } else {
            deliverResult(mangas);
        }
    }

    @Override
    public List<Manga> loadInBackground() {
        if(args == "ALL") {
            return MangaHttp.searchMangas();
        }else{
            return MangaHttp.searchManga(args);
        }
    }
}