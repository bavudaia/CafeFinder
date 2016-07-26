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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by bala on 7/21/16.
 */
public class NearbyResponseListener<T> implements Response.Listener<T> {
    private  GoogleMap mMap;
    public  static List<MyPlace> placeList;
    //public NearbyResponseListener(){super();}
    public NearbyResponseListener(GoogleMap mMap , List<MyPlace> list)
    {
        super();
        this.mMap = mMap;
        this.placeList = list;
    }
    private static  final String RESPONSE_TAG = "RESPONSE_TAG";
    @Override
    public void onResponse(T response) {
        try {
            // Display the first 500 characters of the response string.
            Log.d(RESPONSE_TAG,response.toString());
            Singleton.getInstance().placeList = new ArrayList<>();
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
                String id = (String)result.get("id");
                double rating = getRating(result);
                JSONArray photos = new JSONArray();
                List<Photo> photoList = new ArrayList<>();
                if(result.has("photos"))
                {
                    photos = result.getJSONArray("photos");
                    for (int j = 0; j < photos.length(); j++) {
                        List<String> html_attributions = new ArrayList<>();
                        int height = photos.getJSONObject(j).getInt("height");
                        int width = photos.getJSONObject(j).getInt("width");
                        String photo_reference = photos.getJSONObject(j).getString("photo_reference");


                        JSONArray ha = photos.getJSONObject(j).getJSONArray("html_attributions");
                        for (int k = 0; k < ha.length(); k++) {
                            String html_attribution = ha.getString(k);
                            html_attributions.add(html_attribution);
                        }
                        Photo photo = new Photo();
                        photo.setHtml_attributions(html_attributions);
                        photo.setHeight(height);
                        photo.setWidth(width);
                        photo.setPhoto_reference(photo_reference);

                        photoList.add(photo);
                    }
                }
                String vicinity = (String)result.get("vicinity");
                if(rating>1) {
                    MyPlace myPlace = new MyPlace();
                    myPlace.setName(name);
                    myPlace.setRating(rating);
                    myPlace.setLat(lat);
                    myPlace.setLng(lng);
                    myPlace.setId(id);
                    myPlace.setPhotos(photoList);
                    myPlace.setVicinity(vicinity);
                    Singleton.getInstance().placeList.add(myPlace);
                    Log.d(MapsActivity.TAG, lat + " ," + lng + " ," + name + ", " + rating);
                    LatLng selection = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(selection).title(name).snippet("rating :" + rating));
                }
            }
            Collections.sort(Singleton.getInstance().placeList,new MyPlaceComparator());
            Log.d(RESPONSE_TAG,"End of Response Listener");
        }
        catch (JSONException e)
        {
            Log.d(MapsActivity.TAG,"jSONeXCEP"+e.getMessage());
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
