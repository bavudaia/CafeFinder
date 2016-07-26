package com.example.bala.cafefinder;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by bala on 7/25/16.
 */
public class PhotoErrorListener implements Response.ErrorListener{
    @Override
    public void onErrorResponse(VolleyError error) {
      Log.d(PhotoResponseListener.PHOTO_TAG,"Errrr"+error.getMessage());
    }
}
