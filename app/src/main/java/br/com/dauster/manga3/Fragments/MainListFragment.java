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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.dauster.manga3.Adapter.MainListAdapter;
import br.com.dauster.manga3.DetailActivity;
import br.com.dauster.manga3.Http.MangaIntentService;
import br.com.dauster.manga3.R;
import br.com.dauster.manga3.database.DataContract.MangaContract;



public class MainListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 1 ;

    RecyclerView mRecyclerView;
    MainListAdapter mAdapter;
    LoaderManager mLoaderManager;
    BroadcastReceiver mServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mLoaderManager.restartLoader(LOADER_ID,new Bundle(),MainListFragment.this);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mangas_list, container, false);


        if(savedInstanceState == null){

            IntentFilter filter = new IntentFilter(MangaIntentService.SINCRONIZAR);
            LocalBroadcastManager.getInstance(getActivity()).
                    registerReceiver(mServiceReceiver, filter);

            Intent it = new Intent(getActivity(),MangaIntentService.class);
            it.putExtra(MangaIntentService.SINCRONIZAR, true);
            getActivity().startService(it);

        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.lista);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MainListAdapter(getActivity());
        mAdapter.setMangaClickListener(new MainListAdapter.OnMangaClickListener() {
            @Override
            public void onMangaClick(Cursor cursor, int position) {
                cursor.moveToPosition(position);
                Intent it = new Intent(getActivity(), DetailActivity.class);
                it.putExtra(DetailActivity.EXTRA_MANGAID,
                        cursor.getString(cursor.getColumnIndex(MangaContract.COLUMN_HREF)));
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

        mLoaderManager = getLoaderManager();
        mLoaderManager.initLoader(LOADER_ID, null, this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getActivity()).
                unregisterReceiver(mServiceReceiver);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getActivity(),
                    MangaContract.CONTENT_URI,
                    MangaContract.COLUMNS_LIST_MAIN, null, null, null);
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
