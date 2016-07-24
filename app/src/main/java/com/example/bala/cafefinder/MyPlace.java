package com.example.bala.cafefinder;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by bala on 7/22/16.
 */
public class MyPlace implements Parcelable, Serializable{
    private String id;
    private String name;
    private double rating;
    private double lat,lng;
    public MyPlace(){}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getRating() {
        return String.valueOf(rating);
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeDouble(rating);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
    }


    public static final Parcelable.Creator<MyPlace> CREATOR
            = new Parcelable.Creator<MyPlace>() {
        public MyPlace createFromParcel(Parcel in) {
            return new MyPlace(in);
        }

        public MyPlace[] newArray(int size) {
            return new MyPlace[size];
        }
    };

    private MyPlace(Parcel in)
    {
        id = in.readString();
        name = in.readString();
        rating = in.readDouble();
        lat = in.readDouble();
        lng = in.readDouble();

    }


}
