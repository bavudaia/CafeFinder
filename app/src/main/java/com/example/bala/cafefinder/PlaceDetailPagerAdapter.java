package com.example.bala.cafefinder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by bala on 7/23/16.
 */
public class PlaceDetailPagerAdapter extends FragmentStatePagerAdapter{

   // public PlaceDetailPagerAdapter(){super(null);}
    public  PlaceDetailPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DetailFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return NearbyResponseListener.placeList.size();
    }
}
