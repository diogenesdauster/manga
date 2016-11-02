package br.com.dauster.manga3.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.dauster.manga3.Adapter.GalleryAdapter;
import br.com.dauster.manga3.Loader.ChapterSearchById;
import br.com.dauster.manga3.Model.Chapter;
import br.com.dauster.manga3.Model.Page;
import br.com.dauster.manga3.R;


public class PageGalleryFragment extends Fragment {

    public static final int LOADER_ID_DETAIL_MANGA  = 5;
    public static final int LOADER_ID_DETAIL_CURSOR = 6;
    public static final String ATUALIZA = "atualiza" ;

    LoaderManager mLoaderManager;
    GalleryAdapter mAdapter;
    Chapter mChapter;
    List<Page> mPages;

    LoaderManager.LoaderCallbacks<Chapter> mChapterLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Chapter>() {
        @Override
        public Loader<Chapter> onCreateLoader(int id, Bundle args) {
            // executa o loader
            Chapter chapter = (Chapter) args.getSerializable(ChapterDetailFragment.CHAPTER_INFO);
            return new ChapterSearchById(getActivity(),chapter.getChapterId(),chapter.getHref());
        }

        @Override
        public void onLoadFinished(Loader<Chapter> loader, Chapter chapter) {
            mChapter.setPages(chapter.getPages());
            mChapter.setLastUpdate(chapter.getLastUpdate());
            mPages.clear();
            mPages.addAll(mChapter.getPages());
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<Chapter> loader) {
            // Não implementado
        }
    };

//    LoaderManager.LoaderCallbacks<Cursor>  mChapterCursorLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
//        @Override
//        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//            Chapter chapter = (Chapter) args.getSerializable(ChapterDetailFragment.CHAPTER_INFO);
//            return new CursorLoader(getActivity(),
//                    ContentUris.withAppendedId(DataContract.ChapterContract.CONTENT_URI_MANGAID,)
//                    DataContract.buildUri(DataContract.ChapterContract.CONTENT_URI_MANGAID,
//                            query),
//                    DataContract.MangaContract.COLUMNS_LIST_MAIN, null, null, null);
//
//        }
//
//        @Override
//        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//
//        }
//
//        @Override
//        public void onLoaderReset(Loader<Cursor> loader) {
//
//        }
//    }


    public static PageGalleryFragment newInstace(Chapter chapter){
        Bundle args = new Bundle();
        args.putSerializable(ChapterDetailFragment.CHAPTER_INFO, chapter);
        PageGalleryFragment pageGalleryFragment = new PageGalleryFragment();
        pageGalleryFragment.setArguments(args);
        return pageGalleryFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Arquivo de Layout
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        // View do Arquivo de layout que serão alimentadas

        mPages = new ArrayList<>();

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mAdapter = new GalleryAdapter(getActivity(),mPages);
        viewPager.setAdapter(mAdapter);

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mChapter = (Chapter) getArguments().getSerializable(ChapterDetailFragment.CHAPTER_INFO);
        mLoaderManager = getLoaderManager();

//        // Inicializamos mManga (ver onSaveInsatnceState)
//        if (!DataUtil.isSaveChapter(getActivity().getContentResolver(),mChapter.getChapterId(),mChapter.getHref())){
            // Pega o LoderManager para iniciar o loader passando como parametro o id do manga
            Bundle params = new Bundle();
            params.putSerializable(ChapterDetailFragment.CHAPTER_INFO,mChapter);
            mLoaderManager.initLoader(LOADER_ID_DETAIL_MANGA, params, mChapterLoaderCallbacks);
//        } else {
//            // Pega o LoderManager para iniciar o loader passando como parametro o id do manga
//            Bundle params = new Bundle();
//            params.putSerializable(ChapterDetailFragment.CHAPTER_INFO,mChapter);
//            mLoaderManager.initLoader(LOADER_ID_DETAIL_CURSOR, params, mChapterCursorLoaderCallbacks);
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Salva o Manga em caso de virar a tela , só por segurança
        // pois o loader vai substituir se a consulta já estiver feita
        outState.putSerializable(ChapterDetailFragment.CHAPTER_INFO,mChapter);
    }




}
