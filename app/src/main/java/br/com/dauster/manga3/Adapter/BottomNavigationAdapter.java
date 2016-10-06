package br.com.dauster.manga3.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.dauster.manga3.Fragments.MangasListFragment;


public class BottomNavigationAdapter extends FragmentPagerAdapter {

    public BottomNavigationAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Home";
            case 1:
                return "Search";
            case 2:
                return "Favorite";
            default:
                return "";
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                MangasListFragment frag = new MangasListFragment();
                return  frag;
            case 1:
                frag = new MangasListFragment();
                return  frag;
            case 2:
                frag = new MangasListFragment();
                return  frag;
            default:
                frag = new MangasListFragment();
                return  frag;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
