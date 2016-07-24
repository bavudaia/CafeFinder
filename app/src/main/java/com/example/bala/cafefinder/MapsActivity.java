package com.example.bala.cafefinder;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, PlaceSelectionListener
        , GoogleMap.OnMyLocationButtonClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener , com.google.android.gms.location.LocationListener{

    private GoogleMap mMap;
    private PlaceAutocompleteFragment autocompleteFragment;
    public static final String REQ_TAG = "NearbyTag";
    private StringRequest stringRequest;
    private RequestQueue mRequestQueue;
    private Button nextButton;
    private volatile List<MyPlace> placeList;
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private static  final String RESPONSE_TAG = "RESPONSE_TAG";

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 121;
    public static final String LOC_TAG = "LOC_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        placeList = new ArrayList<>();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        nextButton = (Button) findViewById(R.id.recycler_view_button);
        nextButton.setOnClickListener(new NextButtonClickListener(this,ListActivity.class,placeList));

        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager()
                .findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.setRetainInstance(true);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mMap = googleMap;
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setMyLocationEnabled(true);

            // Add a marker in san jose and move the camera

            LatLng sanJose = new LatLng(37.3299096, -121.9046956);
            mMap.addMarker(new MarkerOptions().position(sanJose).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sanJose, 13));

            //handleNewLocation(location);

        } catch (SecurityException se) {
            Log.d(TAG, se.getMessage());
        }
    }

    public static final String TAG = "MapsActivity";

    /**
     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
     */
    @Override
    public void onPlaceSelected(Place place) {
        try {
            Log.d(TAG, "Place Selected: " + place.getName());
            placeList = new ArrayList<>();
            // Format the returned place's details and display them in the TextView.
            LatLng selection = place.getLatLng();
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(selection).title(place.getName().toString())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selection, 13));

            addNearbyCafes(selection);
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    /**
     * Callback invoked when PlaceAutocompleteFragment encounters an error.
     */
    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Helper method to format information about a place nicely.
     */
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    /* Request permission for error handling purposes */
    /*
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }
    */
    @Override
    public boolean onMyLocationButtonClick() {
        try {
            Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
            // Return false so that we don't consume the event and the default behavior still occurs
            // (the camera animates to the user's current position).

            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            handleNewLocation(location);
            LatLng myLoc  = new LatLng(location.getLatitude(), location.getLongitude());
            placeList = new ArrayList<>();
            addNearbyCafes(myLoc);

            Log.d("TAG" , "onResponse ");
        } catch (SecurityException se) {
            Log.d(LOC_TAG, se.getMessage());
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(REQ_TAG);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(LOC_TAG, "Location services connected.");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            Log.d(LOC_TAG, location.toString());
        }
    }
    private Location location;
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOC_TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(LOC_TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)  ;
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    private void handleNewLocation(Location location) {
        Log.d(LOC_TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("Current")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                ;
        mMap.clear();
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));

    }

    private void addNearbyCafes(LatLng selection)
    {
        try{
            if(mRequestQueue == null)
                mRequestQueue = Volley.newRequestQueue(this);
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                + selection.latitude + "%2C"
                + selection.longitude
                + "&radius=" + MyRequest.RADIUS
                + "&type=" + MyRequest.TYPE
                //+ "&rankby=" + MyRequest.RANKBY
                + "&key=" + MyRequest.KEY;
        // Request a string response from the provided URL.
        stringRequest = new StringRequest(Request.Method.GET
                , url
                , new NearbyResponseListener<String>(mMap,placeList)
                , new NearbyErrorListener());
        stringRequest.setTag(REQ_TAG);
        mRequestQueue.add(stringRequest);
    }
        catch (Exception ex) {
        Log.d(TAG, ex.getMessage());
    }

}
}
