package com.example.popularmovies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by vims1 on 7/7/2016.
 *
 * TabedClass class is the tab used the Main Activity to show the uer Now playing , Pouplar and Top Rated movies
 */
public class TabedClass extends FragmentStatePagerAdapter {
    int tabCount;

    MoviesFragment mf1;
    MoviesFragment mf2;
    MoviesFragment mf3;


    public TabedClass(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount=tabCount;

        mf1=new MoviesFragment();mf1.setStringQuery("now_playing");
        mf2=new MoviesFragment();mf2.setStringQuery("popular");
        mf3=new MoviesFragment();mf3.setStringQuery("top_rated");
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0: return mf1;
            case 1: return mf2;
            case 2: return mf3;
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
