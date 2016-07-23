package com.example.bala.cafefinder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends Activity {
    private TextView name;
    private MyPlace place;
    public static final String DETAIL_EXTRA = "Detail Extra";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        place = getIntent().getParcelableExtra(DETAIL_EXTRA);
        name = (TextView)findViewById(R.id.detail_name);
        name.setText(place.getName());
    }
}
