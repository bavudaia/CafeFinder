package com.example.bala.cafefinder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by bala on 7/23/16.
 */
public class DetailFragment extends Fragment {
    private MyPlace place;

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    private TextView name;
    public static final String EXTRA_PLACE_ID = "Extra Place Id";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_detail, container, false);
        if(savedInstanceState!=null)
        {
            place = (MyPlace) savedInstanceState.getSerializable(KEY_PLACE);
        }
        name = (TextView)v.findViewById(R.id.detail_name);
        name.setText(place.getName());
        return v;
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
    private static final String KEY_PLACE = "KeyPlace";
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_PLACE, place);
    }

    public static DetailFragment newInstance(int  pos)
    {
        MyPlace place=  NearbyResponseListener.placeList.get(pos);
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PLACE_ID,place.getId());
        detailFragment.setArguments(args);
        detailFragment.setPlace(place);
        return detailFragment;
    }

    public MyPlace getPlace() {
        return place;
    }

    public void setPlace(MyPlace place) {
        this.place = place;
    }
}