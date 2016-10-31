package br.com.dauster.manga3.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.dauster.manga3.Fragments.MainListFragment;
import br.com.dauster.manga3.Fragments.MainSearchFragment;


public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Home";
            case 1:
                return "Search";
            default:
                return "";
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MainListFragment();
            case 1:
                return new MainSearchFragment();
            default:
                return new MainListFragment();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
