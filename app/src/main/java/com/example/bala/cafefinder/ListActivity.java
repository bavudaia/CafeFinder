package com.example.bala.cafefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.location.places.Place;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ListActivity extends Activity {
    private static List<MyPlace> placeList;
    private RecyclerView recyclerView;
    private PlaceAdapter placeAdapter;

    public static final String EXTRA_INTENT = "PlaceList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_list);

        populatePlaceList();

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        placeAdapter = new PlaceAdapter();
        placeAdapter.setPlaceList(placeList);

        placeAdapter.setListener(new PlaceAdapter.OnPlaceClickListener() {
                                     @Override
                                     public void onPlaceClick(MyPlace place) {
                                         Intent intent = new Intent(ListActivity.this,DetailActivity.class);
                                         intent.putExtra(DetailActivity.DETAIL_EXTRA,place.getId());
                                         startActivity(intent);
                                     }
                                 });

        recyclerView.setAdapter(placeAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    public void populatePlaceList()
    {
        //placeList = (List) getIntent().getParcelableArrayListExtra(EXTRA_INTENT);
        placeList = Singleton.getInstance().placeList;
        Log.d(MapsActivity.TAG,"SecondList Activity");
        Log.d(MapsActivity.TAG, placeList==null?"null":placeList.toString());
        //placeAdapter.notifyDataSetChanged();
    }
}
