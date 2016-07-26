package com.example.bala.cafefinder;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by bala on 7/21/16.
 */
public class NearbyErrorListener implements Response.ErrorListener {

    CoordinatorLayout coordinatorLayout;
    public NearbyErrorListener(CoordinatorLayout coordinatorLayout)
    {
        this.coordinatorLayout = coordinatorLayout;
    }
    @Override
    public void onErrorResponse(VolleyError error) {

        Log.d(MapsActivity.TAG,error.toString());
        final Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No Internet Connection...", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
