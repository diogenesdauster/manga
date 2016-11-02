package br.com.dauster.manga3.Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.dauster.manga3.DetailActivity;
import br.com.dauster.manga3.Loader.MangaSearchById;
import br.com.dauster.manga3.Model.Manga;
import br.com.dauster.manga3.R;
import br.com.dauster.manga3.database.DataContract;
import br.com.dauster.manga3.database.DataContract.MangaContract;
import br.com.dauster.manga3.database.DataUtil;


public class ContentDetailFragment extends Fragment {

    public static final int LOADER_ID_DETAIL_MANGA  = 2;
    public static final int LOADER_ID_DETAIL_CURSOR = 3;
    public static final String MANGA_INFO = "info" ;
    public static final String ATUALIZA = "atualiza" ;

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


    LoaderManager.LoaderCallbacks<Manga> mMangaLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Manga>() {
        @Override
        public Loader<Manga> onCreateLoader(int id, Bundle args) {
            // executa o loader
            String s = args != null ? args.getString(DetailActivity.EXTRA_MANGAID) : null;
            return new MangaSearchById(getContext(),s);
        }

        @Override
        public void onLoadFinished(Loader<Manga> loader, Manga manga) {
            // atualiza as View
            updateUI(manga);
        }

        @Override
        public void onLoaderReset(Loader<Manga> loader) {
            // Não implementado
        }
    };

    LoaderManager.LoaderCallbacks<Cursor> mMangaCursorLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            // executa o loader
            String query = (args != null) ? args.getString(DetailActivity.EXTRA_MANGAID) : "*";
            return new CursorLoader(getActivity(),
                    DataContract.buildUri(DataContract.MangaContract.CONTENT_URI,
                            query),
                    DataContract.MangaContract.COLUMNS_LIST_MAIN, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

            String mangaid =data.getString(data.getColumnIndex(MangaContract.COLUMN_MANGAID));
            String href = data.getString(data.getColumnIndex(MangaContract.COLUMN_HREF));

            List<String> artist = new ArrayList<String>(Arrays.asList(data.getString(data.getColumnIndex(MangaContract.COLUMN_ARTIST)).split(",")));
            List<String> author = new ArrayList<String>(Arrays.asList(data.getString(data.getColumnIndex(MangaContract.COLUMN_AUTHOR)).split(",")));
            List<String> genres = new ArrayList<String>(Arrays.asList(data.getString(data.getColumnIndex(MangaContract.COLUMN_GENRES)).split(",")));

            String cover = data.getString(data.getColumnIndex(MangaContract.COLUMN_COVER));
            String info = data.getString(data.getColumnIndex(MangaContract.COLUMN_INFO));
            String lastupdate = data.getString(data.getColumnIndex(MangaContract.COLUMN_LASTUPDATE));
            String name = data.getString(data.getColumnIndex(MangaContract.COLUMN_NAME));
            String status = data.getString(data.getColumnIndex(MangaContract.COLUMN_STATUS));
            Long yearofrelease = data.getLong(data.getColumnIndex(MangaContract.COLUMN_YEAROFRELEASE));

            Manga manga = new Manga();
            manga.setMangaId(mangaid);
            manga.setHref(href);
            manga.setArtist(artist);
            manga.setAuthor(author);
            manga.setCover(cover);
            manga.setGenres(genres);
            manga.setInfo(info);
            manga.setLastUpdate(lastupdate);
            manga.setName(name);
            manga.setStatus(status);
            manga.setYearOfRelease(yearofrelease);

            updateUI(manga);

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };



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

        // Arquivo de Layout
        View view = inflater.inflate(R.layout.fragment_content_detail, container, false);

        // View do Arquivo de layout que serão alimentadas
        mImgCover   = (ImageView) view.findViewById(R.id.imgCover);
        mTextAuthor = (TextView) view.findViewById(R.id.textAuthor);
        mTextStatus = (TextView) view.findViewById(R.id.textStatus);
        mTextName   = (TextView) view.findViewById(R.id.textName);
        mTextLastUpd= (TextView) view.findViewById(R.id.textLastUpd);
        mTextGenre  = (TextView) view.findViewById(R.id.textGenre);
        mTextInfo   = (TextView) view.findViewById(R.id.textInfo);

        // Toolbar da Activity para mudar nome de acordo com o manga
        mToolbar    = (Toolbar) getActivity().findViewById(R.id.toolbar);

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mManga = (Manga) getArguments().getSerializable(MANGA_INFO);
        mLoaderManager = getLoaderManager();


        // Inicializamos mManga (ver onSaveInsatnceState)
        if (!DataUtil.isSaveManga(getActivity().getContentResolver(),mManga.getMangaId())){
            // Pega o LoderManager para iniciar o loader passando como parametro o id do manga
            Bundle params = new Bundle();
            params.putString(DetailActivity.EXTRA_MANGAID,mManga.getMangaId());
            mLoaderManager.initLoader(LOADER_ID_DETAIL_MANGA, params, mMangaLoaderCallbacks);
        } else {
            // Pega o LoderManager para iniciar o loader passando como parametro o id do manga
            Bundle params = new Bundle();
            params.putString(DetailActivity.EXTRA_MANGAID,mManga.getMangaId());
            mLoaderManager.initLoader(LOADER_ID_DETAIL_CURSOR, params, mMangaCursorLoaderCallbacks);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Salva o Manga em caso de virar a tela , só por segurança
        // pois o loader vai substituir se a consulta já estiver feita
        outState.putSerializable(MANGA_INFO,mManga);
    }


    private void updateUI(Manga manga){

        // Atualiza o manga se o não for null

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

            DataUtil.sendMessage(ATUALIZA,true,getActivity());

        }

    }


}
