package com.example.bala.cafefinder;

import java.util.Comparator;

/**
 * Created by bala on 7/23/16.
 */
public class MyPlaceComparator implements Comparator<MyPlace> {

    @Override
    public int compare(MyPlace t1, MyPlace t2) {
        if(Double.parseDouble(t1.getRating()) > Double.parseDouble(t2.getRating()))
        {
            return -1;
        }
        else if(Double.parseDouble(t1.getRating()) == Double.parseDouble(t2.getRating()))
        {
            return 0;
        }
        return 1;
    }
}
