package com.test.myapplication;

import java.util.ArrayList;

public class segmentTimes {

    public String name = null;
    public ArrayList<String> times= new ArrayList<>();

    public static segmentTimes segmenttimes = new segmentTimes();

    public static segmentTimes getInstance(){
        return segmenttimes;
    }
}
