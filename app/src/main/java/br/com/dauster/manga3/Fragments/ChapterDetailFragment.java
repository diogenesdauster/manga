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

import br.com.dauster.manga3.Adapter.ChapterDetailAdapter;
import br.com.dauster.manga3.Model.Chapter;
import br.com.dauster.manga3.R;


public class ChapterDetailFragment extends Fragment  {


    List<Chapter> mChapters;
    ChapterDetailAdapter mAdapter;
//    Fragment contentDetailFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chapter_detail, container, false);

//        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewPagerDetail);
//
//        ViewPagerDetailAdapter viewPagerDetailAdapter =
//                (ViewPagerDetailAdapter) viewPager.getAdapter();
//
//        contentDetailFragment = (Fragment) viewPagerDetailAdapter.getItem(1);

        mChapters = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listDetailChapter);
        recyclerView.setHasFixedSize(true);

        mAdapter = new ChapterDetailAdapter(getActivity(),mChapters);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);



        return view;
    }


//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//
//        if(position == 1) {
//
//            Manga manga = (Manga) contentDetailFragment.getArguments().
//                    getSerializable(ContentDetailFragment.MANGA_INFO);
//            mChapters = manga.getChapters();
//            mAdapter.notifyDataSetChanged();
//
//        }
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }
}
