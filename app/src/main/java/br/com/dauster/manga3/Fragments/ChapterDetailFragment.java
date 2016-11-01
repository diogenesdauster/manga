package br.com.dauster.manga3.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.dauster.manga3.Adapter.DetailListAdapter;
import br.com.dauster.manga3.Model.Chapter;
import br.com.dauster.manga3.R;


public class ChapterDetailFragment extends Fragment  {


    List<Chapter> mChapters;
    DetailListAdapter mAdapter;
//    Fragment contentDetailFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chapter_detail, container, false);


        mChapters = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listDetailChapter);
        recyclerView.setHasFixedSize(true);

        mAdapter = new DetailListAdapter(getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);



        return view;
    }


}
