package br.com.dauster.manga3.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.dauster.manga3.Fragments.ChapterDetailFragment;
import br.com.dauster.manga3.Fragments.ContentDetailFragment;


public class ViewPagerDetailAdapter extends FragmentPagerAdapter {


    public ViewPagerDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                ContentDetailFragment fragDetail = new ContentDetailFragment();
                return fragDetail;
            case 1:
                ChapterDetailFragment fragChapter = new ChapterDetailFragment();
                return fragChapter;
            default:
                fragDetail = new ContentDetailFragment();
                return fragDetail;
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Info";
            case 1:
                return "Chapter";
            default:
                return "Info";
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
