package br.com.dauster.manga3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import java.util.ArrayList;
import java.util.List;

import br.com.dauster.manga3.Adapter.MangaAdapter;
import br.com.dauster.manga3.Model.Manga;
import br.com.dauster.manga3.Model.MangaHttp;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView mRecyclerView;
    MangaAdapter mAdapter;
    List<Manga> mMangasList;
    BottomNavigationView bottomNavigationView;

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
        new MangaTask().execute("ALL");

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

        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                if(index == 0)
                new MangaTask().execute("ALL");
            }
        });

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
        new MangaTask().execute(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return true;
    }

    private class MangaTask extends AsyncTask<String,Void,List<Manga>> {
        @Override
        protected List<Manga> doInBackground(String... strings) {
            if(strings[0] == "ALL") {
                return MangaHttp.searchMangas();
            }else{
                return MangaHttp.searchManga(strings[0]);
            }
        }

        @Override
        protected void onPostExecute(List<Manga> mangas) {
            super.onPostExecute(mangas);
            if(mMangasList != null && mangas.size() > 0){
                mMangasList.clear();
                mMangasList.addAll(mangas);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
