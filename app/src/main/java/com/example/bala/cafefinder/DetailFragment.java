package com.example.bala.cafefinder;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by bala on 7/23/16.
 */
public class DetailFragment extends Fragment {
    private MyPlace place;

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    private TextView name;
    private TextView rating;
    private TextView vicinity;
    private ImageView imageView;
    private RequestQueue rq;
    public static final String EXTRA_PLACE_ID = "Extra Place Id";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_detail, container, false);
        if(savedInstanceState!=null)
        {
            place = (MyPlace) savedInstanceState.getSerializable(KEY_PLACE);
        }
        name = (TextView)v.findViewById(R.id.detail_name);
        name.setText(place.getName());

        rating = (TextView)v.findViewById(R.id.rating);
        rating.setText("Rating : "+place.getRating());

        vicinity = (TextView)v.findViewById(R.id.vicinity);
        vicinity.setText(place.getVicinity());

        imageView = (ImageView)v.findViewById(R.id.image);

        getImage(place);

        return v;
    }

    private void getImage(MyPlace place )
    {
        List<Photo> photos = place.getPhotos();
        if(photos == null || photos.size() ==0)
            return;
        rq = Volley.newRequestQueue(getContext());
        String url = "https://maps.googleapis.com/maps/api/place/photo?key="
                + MyRequest.KEY
                +"&photoreference="+photos.get(0).getPhoto_reference()
                +"&maxheight="+PhotoAttr.max_height
                +"&maxwidth"  + PhotoAttr.max_width
                ;
        // Request a string response from the provided URL.

        ImageRequest imageRequest = new ImageRequest(
                 url
                , new PhotoResponseListener(imageView)
                , 0,0,null,new PhotoErrorListener());
        /*
        StringRequest stringRequest = new StringRequest(Request.Method.GET
                , url
                , new PhotoResponseListener(imageView)
                , new PhotoErrorListener());
                */
        imageRequest.setTag(P_REQ_TAG);
        rq.add(imageRequest);
    }
  private static final String P_REQ_TAG = "Photo Request Tag";
    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
    private static final String KEY_PLACE = "KeyPlace";
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_PLACE, place);
    }

    public static DetailFragment newInstance(int  pos)
    {
        MyPlace place=  Singleton.getInstance().placeList.get(pos);
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PLACE_ID,place.getId());
        detailFragment.setArguments(args);
        detailFragment.setPlace(place);
        return detailFragment;
    }

    public MyPlace getPlace() {
        return place;
    }

    public void setPlace(MyPlace place) {
        this.place = place;
    }
}