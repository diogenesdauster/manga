package br.com.dauster.manga3.Loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import br.com.dauster.manga3.Http.MangaApi;
import br.com.dauster.manga3.Model.Chapter;
import br.com.dauster.manga3.database.DataUtil;


public class ChapterSearchById extends AsyncTaskLoader<Chapter> {

    Chapter  mChapter;
    Long mChapterId;
    String mMangaId;


    public ChapterSearchById(Context context, Long chapterId,String mangaId) {
        super(context);
        this.mChapterId = chapterId;
        this.mMangaId   = mangaId;
        this.mChapter   = new Chapter();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(mChapterId != null){
            forceLoad();
        }else{
            deliverResult(mChapter);
        }
    }

    @Override
    public Chapter loadInBackground() {
        mChapter = MangaApi.searchChapterById(mChapterId,mMangaId);
        if (mChapter != null) {
            DataUtil.handleChapter(getContext().getContentResolver(), mChapter);
        }
        mChapterId = null;
        return mChapter;
    }
}
