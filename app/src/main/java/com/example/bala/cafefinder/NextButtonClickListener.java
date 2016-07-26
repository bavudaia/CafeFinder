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
    private static List<MyPlace> placeList;
    public NextButtonClickListener(Context from , Class to, List<MyPlace> list)
    {
        fromActivity=from;
        toActivity = to;
        placeList = list;
    }
    @Override
    public void onClick(View view) {
        if(Singleton.getInstance().placeList!=null && Singleton.getInstance().placeList.size()>0 ) {
            Intent intent = new Intent(fromActivity, toActivity);
            intent.putParcelableArrayListExtra(ListActivity.EXTRA_INTENT, (ArrayList<MyPlace>) Singleton.getInstance().placeList);
            fromActivity.startActivity(intent);
        }
    }
}
