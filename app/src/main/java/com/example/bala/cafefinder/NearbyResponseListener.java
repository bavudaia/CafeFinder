package com.example.bala.cafefinder;

import android.util.Log;

import com.android.volley.Response;
import com.google.android.gms.drive.realtime.internal.event.ObjectChangedDetails;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by bala on 7/21/16.
 */
public class NearbyResponseListener<T> implements Response.Listener<T> {
    private GoogleMap mMap;
    private List<MyPlace> placeList;
    public NearbyResponseListener(){super();}
    public NearbyResponseListener(GoogleMap mMap , List<MyPlace> list)
    {
        super();
        this.mMap = mMap;
        this.placeList = list;
    }
    @Override
    public void onResponse(T response) {
        try {
            // Display the first 500 characters of the response string.
            JSONObject json = new JSONObject((String) response);
            JSONArray results = new JSONArray(json.get("results").toString());
            int resLen = results.length();
            for(int i=0;i<resLen;i++)
            {
                JSONObject result = new JSONObject(results.get(i).toString());
                JSONObject geo = new JSONObject(result.get("geometry").toString());
                JSONObject location = new JSONObject(geo.get("location").toString());
                double lat = (double)location.get("lat");

                double lng = (double)location.get("lng");

                String name = (String)result.get("name");
                double rating = getRating(result);
                if(rating>1) {

                    MyPlace myPlace = new MyPlace();
                    myPlace.setName(name);
                    myPlace.setRating(rating);
                    myPlace.setLat(lat);
                    myPlace.setLng(lng);

                    placeList.add(myPlace);
                    Log.d(MapsActivity.TAG, lat + " ," + lng + " ," + name + ", " + rating);
                    LatLng selection = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(selection).title(name).snippet("rating :" + rating));
                }
            }
        }
        catch (JSONException e)
        {
            Log.d(MapsActivity.TAG,e.getMessage());
        }
    }

    private  double getRating(JSONObject result) throws  JSONException
    {

        Object ratingObj = null;
        if(result.has("rating"))
            ratingObj = result.get("rating");

        double rating = 0.0;
        if(ratingObj != null) {
            if (ratingObj instanceof Integer) {
                rating = (Integer) ratingObj;

            } else if (ratingObj instanceof Double) {
                rating = (Double) ratingObj;
            } else if (ratingObj instanceof Float) {
                rating = (Float) ratingObj;
            }
        }

        return rating;
    }
}
