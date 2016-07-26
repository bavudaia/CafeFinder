package com.example.bala.cafefinder;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bala on 7/25/16.
 */
public class Photo implements Parcelable {
    private List<String> html_attributions;
    private int height,width;
    private String photo_reference;
    public Photo(){}
    protected Photo(Parcel in) {
        html_attributions = in.createStringArrayList();
        height = in.readInt();
        width = in.readInt();
        photo_reference = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public List<String> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(List<String> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(html_attributions);
        parcel.writeInt(height);
        parcel.writeInt(width);
        parcel.writeString(photo_reference);
    }
}
