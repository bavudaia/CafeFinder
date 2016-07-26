package com.example.bala.cafefinder;

import android.app.Activity;
import android.util.Log;

/**
 * Created by bala on 7/24/16.
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler
{
    private final Activity mContext;

    public ExceptionHandler(Activity mContext)
    {
        this.mContext = mContext;
    }
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.d(MapsActivity.TAG, "Uncaught Exception" + throwable.getMessage());
    }
}
