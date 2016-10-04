package br.com.dauster.manga3;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.dauster.manga3.Loader.MangaSearchById;
import br.com.dauster.manga3.Model.Manga;

public class MangaDetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Manga>{

    public static final String EXTRA_MANGAID ="mangaId" ;
    private static final int LOADER_ID_DETAIL =2 ;

    ImageView mImgCover;
    TextView  mTextAuthor;
    TextView  mTextStatus;
    TextView  mTextName;
    TextView  mTextLastUpd;
    TextView  mTextGenre;
    TextView  mTextInfo;
    LoaderManager mLoaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_manga_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        mImgCover = (ImageView) findViewById(R.id.imgCover);
        mTextAuthor = (TextView) findViewById(R.id.textAuthor);
        mTextStatus = (TextView) findViewById(R.id.textStatus);
        mTextName = (TextView) findViewById(R.id.textName);
        mTextLastUpd = (TextView) findViewById(R.id.textLastUpd);
        mTextGenre = (TextView) findViewById(R.id.textGenre);
        mTextInfo = (TextView) findViewById(R.id.textInfo);

        String mangaid = getIntent().getExtras().getString(EXTRA_MANGAID);

        mLoaderManager = getSupportLoaderManager();
        Bundle params = getIntent().getExtras();
        mLoaderManager.initLoader(LOADER_ID_DETAIL, params, this);

    }


    private Context getContext(){
        return this;
    }

    @Override
    public Loader<Manga> onCreateLoader(int id, Bundle args) {
        String s = args != null ? args.getString(EXTRA_MANGAID) : null;
        return new MangaSearchById(this,s);
    }

    @Override
    public void onLoadFinished(Loader<Manga> loader, Manga manga) {
        if(manga != null){
            Glide.with(getContext()).load(manga.getCover()).
                    placeholder(R.drawable.ic_do_not_disturb_black_200dp).
                    into(mImgCover);
//                mTextAuthor.setText((CharSequence) manga.getAuthor());
            //mTextStatus.setText(m.getGenres().);
            mTextName.setText(manga.getName());
            mTextLastUpd.setText(manga.getLastUpdate());
            //mTextGenre.setText(.getGenres());
            mTextInfo.setText(manga.getInfo());

        }

    }

    @Override
    public void onLoaderReset(Loader<Manga> loader) {

    }

}
