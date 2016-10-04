package br.com.dauster.manga3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import br.com.dauster.manga3.Adapter.MangaAdapter;
import br.com.dauster.manga3.Loader.MangaSearchTask;
import br.com.dauster.manga3.Model.Manga;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        LoaderManager.LoaderCallbacks<List<Manga>>{

    private static final String MANGA_ARGS = "args" ;
    private static final int LOADER_ID = 1 ;
    RecyclerView mRecyclerView;
    MangaAdapter mAdapter;
    List<Manga> mMangasList;
    BottomNavigationView bottomNavigationView;
    LoaderManager mLoaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMangasList = new ArrayList<>();

        mAdapter = new MangaAdapter(this,mMangasList);

        mAdapter.setmMangaClickListener(new MangaAdapter.OnMangaClickListener() {
            @Override
            public void onMangaClick(Manga manga, int position) {
                Intent it = new Intent(MainActivity.this, MangaDetailActivity.class);
                it.putExtra(MangaDetailActivity.EXTRA_MANGAID, manga.getMangaId());
                startActivity(it);
            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.lista);
        mRecyclerView.setHasFixedSize(true);

        //StaggeredGridLayoutManager mStaggeredLayoutManager = new
        //        StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        //mStaggeredLayoutManager.setSpanCount(2);
        //mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setAdapter(mAdapter);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        mLoaderManager = getSupportLoaderManager();
        Bundle params = new Bundle();
        params.putString(MANGA_ARGS, "ALL");
        mLoaderManager.initLoader(LOADER_ID, params, this);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);

        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
                ("Home", ContextCompat.getColor(this, R.color.colorPrimary), R.drawable.ic_view_comfy_black_24dp);
        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
                ("Favorite", ContextCompat.getColor(this, R.color.colorPrimary), R.drawable.ic_star_border_black_24dp);
        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem
                ("Search", ContextCompat.getColor(this, R.color.colorPrimary), R.drawable.ic_search_black_24dp);
        bottomNavigationView.addTab(bottomNavigationItem);
        bottomNavigationView.addTab(bottomNavigationItem1);
        bottomNavigationView.addTab(bottomNavigationItem2);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manga_search,menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView  searchView= (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        bottomNavigationView.selectTab(2);

        LoaderManager lm = getSupportLoaderManager();
        Bundle params = new Bundle();
        params.putString(MANGA_ARGS, s);
        lm.restartLoader(LOADER_ID, params, this);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return true;
    }


    @Override
    public Loader<List<Manga>> onCreateLoader(int id, Bundle args) {

        String s = args != null ? args.getString(MANGA_ARGS) : null;
        return new MangaSearchTask(this, s);
    }


    @Override
    public void onLoadFinished(Loader<List<Manga>> loader, List<Manga> mangas) {
        if(mMangasList != null && mangas.size() > 0){
            mMangasList.clear();
            mMangasList.addAll(mangas);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Manga>> loader) {

    }

}
