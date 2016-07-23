package com.example.bala.cafefinder;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bala on 7/22/16.
 */
public class NextButtonClickListener implements View.OnClickListener{
    private Context fromActivity;
    private Class toActivity;
    private List<MyPlace> placeList;
    public NextButtonClickListener(Context from , Class to, List<MyPlace> list)
    {
        fromActivity=from;
        toActivity = to;
        placeList = list;
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(fromActivity,toActivity);
        intent.putParcelableArrayListExtra(ListActivity.EXTRA_INTENT,(ArrayList<MyPlace>)placeList);
        fromActivity.startActivity(intent);
    }
}
