package com.example.bala.cafefinder;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by bala on 7/21/16.
 */
public class NearbyErrorListener implements Response.ErrorListener {
    @Override
    public void onErrorResponse(VolleyError error) {


        Log.d(MapsActivity.TAG,error.toString());
    }
}
