package br.com.dauster.manga3.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.dauster.manga3.Fragments.MangasMainListAllFragment;


public class ViewPagerBottomNavigationAdapter extends FragmentPagerAdapter {

    public ViewPagerBottomNavigationAdapter(FragmentManager fm) {
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
                MangasMainListAllFragment frag = new MangasMainListAllFragment();
                return  frag;
            case 1:
                frag = new MangasMainListAllFragment();
                return  frag;
            case 2:
                frag = new MangasMainListAllFragment();
                return  frag;
            default:
                frag = new MangasMainListAllFragment();
                return  frag;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
