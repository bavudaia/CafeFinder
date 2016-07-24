package com.example.bala.cafefinder;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.List;

public class DetailActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private List<MyPlace> placeList;
    public static final String DETAIL_EXTRA = "Detail Extra";
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        //setContentView(R.layout.activity_detail);

        FragmentManager fm = getSupportFragmentManager();
        PlaceDetailPagerAdapter pdpa = new PlaceDetailPagerAdapter(fm);

        mViewPager.setAdapter(pdpa);
        id = (String) getIntent().getSerializableExtra(DetailActivity.DETAIL_EXTRA);
        if(id==null)
        {
            id = savedInstanceState.getString(KEY_INDEX);
        }
        int size = NearbyResponseListener.placeList.size();
        for(int i=0;i<size;i++)
        {
            if(NearbyResponseListener.placeList.get(i).getId().equals(id))
            {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void  onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_INDEX,id);
    }

    private static final String KEY_INDEX = "index";
}
