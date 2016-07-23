package com.example.bala.cafefinder;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;

import java.util.List;

/**
 * Created by bala on 7/22/16.
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {


    private List<MyPlace> placeList;
    public List<MyPlace> getPlaceList() {
        return placeList;
    }
    private OnPlaceClickListener listener;
    public void setPlaceList(List<MyPlace> placeList) {
        this.placeList = placeList;
    }
    public interface OnPlaceClickListener {
        void onPlaceClick(MyPlace place);
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder{
        public TextView name,rating;
        private View view;
        public PlaceViewHolder(View view)
        {
            super(view);
            this.view = view;
            name = (TextView)view.findViewById(R.id.place_name);
            rating = (TextView)view.findViewById(R.id.place_rating);
        }

        public void setListener(final MyPlace place)
        {
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    listener.onPlaceClick(place);
                }
            });
        }
    }
    public PlaceAdapter() {}

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_list_low, parent, false);
        return new PlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int i) {
        MyPlace place = placeList.get(i);
        holder.name.setText(place.getName());
        holder.rating.setText("Rating: "+place.getRating());
        holder.setListener(place);
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public OnPlaceClickListener getListener() {
        return listener;
    }

    public void setListener(OnPlaceClickListener listener) {
        this.listener = listener;
    }
}
