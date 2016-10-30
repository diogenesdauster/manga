package br.com.dauster.manga3.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.dauster.manga3.Loader.MangaSearchById;
import br.com.dauster.manga3.MangaDetailActivity;
import br.com.dauster.manga3.Model.Manga;
import br.com.dauster.manga3.R;
import br.com.dauster.manga3.database.DataUtil;

public class ContentDetailFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Manga>{

    private static final int LOADER_ID_DETAIL = 2;
    private static final String MANGA_INFO = "info" ;

    LoaderManager mLoaderManager;
    ImageView mImgCover;
    TextView  mTextLastUpd;
    TextView  mTextGenre;
    TextView  mTextInfo;
    TextView  mTextAuthor;
    TextView  mTextStatus;
    TextView  mTextName;
    Toolbar   mToolbar;
    Manga     mManga;



    public static ContentDetailFragment newInstace(Manga manga){
        Bundle args = new Bundle();
        args.putSerializable(MANGA_INFO, manga);
        ContentDetailFragment contentDetailFragment = new ContentDetailFragment();
        contentDetailFragment.setArguments(args);
        return contentDetailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content_detail, container, false);

        mImgCover   = (ImageView) view.findViewById(R.id.imgCover);
        mTextAuthor = (TextView) view.findViewById(R.id.textAuthor);
        mTextStatus = (TextView) view.findViewById(R.id.textStatus);
        mTextName   = (TextView) view.findViewById(R.id.textName);
        mTextLastUpd= (TextView) view.findViewById(R.id.textLastUpd);
        mTextGenre  = (TextView) view.findViewById(R.id.textGenre);
        mTextInfo   = (TextView) view.findViewById(R.id.textInfo);
        mToolbar    = (Toolbar) getActivity().findViewById(R.id.toolbar);

        // Inicializamos mMovie (ver onSaveInsatnceState)
        if (savedInstanceState == null){
            // Se não tem um estado anterior, use o que foi passado no método newInstance.
            mManga = (Manga) getArguments().getSerializable(MANGA_INFO);
        } else {
            // Se há um estado anterior, use-o
            mManga = (Manga) savedInstanceState.getSerializable(MANGA_INFO);
        }


        mLoaderManager = getLoaderManager();
        Bundle params = new Bundle();
        params.putString(MangaDetailActivity.EXTRA_MANGAID,mManga.getHref());
        mLoaderManager.initLoader(LOADER_ID_DETAIL, params, this);


        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(MANGA_INFO,mManga);
    }


    @Override
    public Loader<Manga> onCreateLoader(int id, Bundle args) {
        String s = args != null ? args.getString(MangaDetailActivity.EXTRA_MANGAID) : null;
        return new MangaSearchById(getContext(),s);
    }

    @Override
    public void onLoadFinished(Loader<Manga> loader, Manga manga) {
        if(manga != null){
            mManga = manga;
            mTextLastUpd.setText(manga.getLastUpdate());
            mTextGenre.setText(DataUtil.listToString(manga.getGenres()));
            mTextInfo.setText(manga.getInfo());
            mTextAuthor.setText(DataUtil.listToString(manga.getAuthor()));
            mTextStatus.setText(manga.getStatus());
            mTextName.setText(manga.getName());
            mToolbar.setTitle(manga.getName());
            Glide.with(getContext()).load(manga.getCover()).
                    placeholder(R.drawable.ic_do_not_disturb_black_200dp).
                    into(mImgCover);
        }

    }

    @Override
    public void onLoaderReset(Loader<Manga> loader) {

    }



}
