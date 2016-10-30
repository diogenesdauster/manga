package br.com.dauster.manga3.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.dauster.manga3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChapterDetailFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chapter_detail, container, false);






        return view;
    }

}
