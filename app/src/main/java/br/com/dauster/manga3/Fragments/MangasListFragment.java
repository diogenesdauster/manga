package br.com.dauster.manga3.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import br.com.dauster.manga3.Adapter.MangaAdapter;
import br.com.dauster.manga3.Loader.MangaSearchTask;
import br.com.dauster.manga3.MangaDetailActivity;
import br.com.dauster.manga3.Model.Manga;
import br.com.dauster.manga3.R;

public class MangasListFragment extends Fragment implements SearchView.OnQueryTextListener,
        LoaderManager.LoaderCallbacks<List<Manga>> {

    private static final String MANGA_ARGS = "args" ;
    private static final int LOADER_ID = 1 ;
    RecyclerView mRecyclerView;
    MangaAdapter mAdapter;
    List<Manga> mMangasList;
    LoaderManager mLoaderManager;
    BottomNavigationView bottomNavigationView;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mMangasList = new ArrayList<>();
        mAdapter = new MangaAdapter(getActivity(),mMangasList);
        mAdapter.setmMangaClickListener(new MangaAdapter.OnMangaClickListener() {
            @Override
            public void onMangaClick(Manga manga, int position) {
                Intent it = new Intent(getActivity(), MangaDetailActivity.class);
                it.putExtra(MangaDetailActivity.EXTRA_MANGAID, manga.getMangaId());
                startActivity(it);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mangas_list, container, false);

        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottomNavigation);

        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
                ("Home", ContextCompat.getColor(getActivity(), R.color.colorPrimary), R.drawable.ic_view_comfy_black_24dp);
        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
                ("Favorite", ContextCompat.getColor(getActivity(), R.color.colorPrimary), R.drawable.ic_star_border_black_24dp);
        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem
                ("Search", ContextCompat.getColor(getActivity(), R.color.colorPrimary), R.drawable.ic_search_black_24dp);
        bottomNavigationView.addTab(bottomNavigationItem);
        bottomNavigationView.addTab(bottomNavigationItem1);
        bottomNavigationView.addTab(bottomNavigationItem2);



        mRecyclerView = (RecyclerView) view.findViewById(R.id.lista);
        mRecyclerView.setHasFixedSize(true);

        //StaggeredGridLayoutManager mStaggeredLayoutManager = new
        //        StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        //mStaggeredLayoutManager.setSpanCount(2);
        //mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRecyclerView.setAdapter(mAdapter);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        mLoaderManager = getActivity().getSupportLoaderManager();
        Bundle params = new Bundle();
        params.putString(MANGA_ARGS, "ALL");
        mLoaderManager.initLoader(LOADER_ID, params, this);


        return view;
    }

    @Override
    public Loader<List<Manga>> onCreateLoader(int id, Bundle args) {
        String s = args != null ? args.getString(MANGA_ARGS) : null;
        return new MangaSearchTask(getActivity(), s);
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_manga_search,menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView  searchView= (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Bundle params = new Bundle();
        params.putString(MANGA_ARGS, query);
        mLoaderManager.restartLoader(LOADER_ID, params, this);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
