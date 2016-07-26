package com.example.bala.cafefinder;

import java.util.List;

/**
 * Created by bala on 7/24/16.
 */
public class Singleton {
    private static Singleton instance;
    public static List<MyPlace> placeList;
    private Singleton(){}
    public static Singleton getInstance()
    {
        if(instance==null)
        {
            synchronized (Singleton.class) {
                if(instance == null)
                    instance = new Singleton();
            }
        }
        return instance;
    }
}
