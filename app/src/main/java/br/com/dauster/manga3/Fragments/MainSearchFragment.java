package br.com.dauster.manga3.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
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

import br.com.dauster.manga3.Adapter.MainListAdapter;
import br.com.dauster.manga3.DetailActivity;
import br.com.dauster.manga3.R;
import br.com.dauster.manga3.database.DataContract;

public class MainSearchFragment extends Fragment implements SearchView.OnQueryTextListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String MANGA_ARGS = "args" ;
    private static final int LOADER_ID = 2 ;
    RecyclerView mRecyclerView;
    MainListAdapter mAdapter;
    LoaderManager mLoaderManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mangas_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.lista);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MainListAdapter(getActivity());
        mAdapter.setMangaClickListener(new MainListAdapter.OnMangaClickListener() {
            @Override
            public void onMangaClick(Cursor cursor, int position) {
                cursor.moveToPosition(position);
                Intent it = new Intent(getActivity(), DetailActivity.class);
                it.putExtra(DetailActivity.EXTRA_MANGAID,
                        cursor.getString(cursor.getColumnIndex(DataContract.MangaContract.COLUMN_HREF)));
                startActivity(it);
            }
        });

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mLoaderManager = getLoaderManager();
        mLoaderManager.initLoader(LOADER_ID, null, this);

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
        mLoaderManager.restartLoader(LOADER_ID,params,this);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String query;

        if(args == null){
            query = "*";
        }else {
            query = args.getString(MANGA_ARGS);
        }

            return new CursorLoader(getActivity(),
                    DataContract.buildUri(DataContract.MangaContract.CONTENT_URI_NAME,
                            query),
                    DataContract.MangaContract.COLUMNS_LIST_MAIN, null, null, null);
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
