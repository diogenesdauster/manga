package br.com.dauster.manga3.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.dauster.manga3.Adapter.DetailListAdapter;
import br.com.dauster.manga3.DetailActivity;
import br.com.dauster.manga3.Http.MangaIntentService;
import br.com.dauster.manga3.Model.Manga;
import br.com.dauster.manga3.R;
import br.com.dauster.manga3.database.DataContract;


public class ChapterDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int LOADER_ID_DETAIL_CHAPTER = 4 ;
    LoaderManager mLoaderManager;
    DetailListAdapter mAdapter;
    Manga mManga;

    BroadcastReceiver mServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle params = new Bundle();
            params.putString(DetailActivity.EXTRA_MANGAID,mManga.getMangaId());
            mLoaderManager.restartLoader(LOADER_ID_DETAIL_CHAPTER, params, ChapterDetailFragment.this);

        }
    };


    public static ChapterDetailFragment newInstace(Manga manga){
        Bundle args = new Bundle();
        args.putSerializable(ContentDetailFragment.MANGA_INFO, manga);
        ChapterDetailFragment chapterDetailFragment = new ChapterDetailFragment();
        chapterDetailFragment.setArguments(args);
        return chapterDetailFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chapter_detail, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listDetailChapter);
        recyclerView.setHasFixedSize(true);

        mAdapter = new DetailListAdapter(getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoaderManager = getLoaderManager();

        mManga = (Manga) getArguments().getSerializable(ContentDetailFragment.MANGA_INFO);


        IntentFilter filter = new IntentFilter(MangaIntentService.SINCRONIZAR);
        LocalBroadcastManager.getInstance(getActivity()).
                registerReceiver(mServiceReceiver, filter);


        Bundle params = new Bundle();
        params.putString(DetailActivity.EXTRA_MANGAID,mManga.getMangaId());
        mLoaderManager.initLoader(LOADER_ID_DETAIL_CHAPTER, params, this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ContentDetailFragment.MANGA_INFO,mManga);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).
                unregisterReceiver(mServiceReceiver);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // executa o loader
        String query = (args != null) ? args.getString(DetailActivity.EXTRA_MANGAID) : "*";
        return new CursorLoader(getActivity(),
                DataContract.buildUri(DataContract.ChapterContract.CONTENT_URI_MANGAID,
                        query),
                DataContract.ChapterContract.COLUMNS_LIST_MAIN, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
