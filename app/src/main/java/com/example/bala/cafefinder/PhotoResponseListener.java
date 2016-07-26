package com.example.bala.cafefinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Response;

/**
 * Created by bala on 7/25/16.
 */
public class PhotoResponseListener implements Response.Listener<Bitmap>  {
    public static  final String PHOTO_TAG = "PhotoTag";
    private ImageView imageView;
    public PhotoResponseListener(){}
    public PhotoResponseListener(ImageView imageView)
    {
        this.imageView = imageView;
    }
    @Override
    public void onResponse(Bitmap response) {

        Log.d(PHOTO_TAG,response.toString());

 /*           byte[] respBytes = response.getBytes();
        response.
        Bitmap bitmap = BitmapFactory.decodeByteArray(respBytes , 0, respBytes.length);
 */
        imageView.setImageBitmap(response);
    }
}
