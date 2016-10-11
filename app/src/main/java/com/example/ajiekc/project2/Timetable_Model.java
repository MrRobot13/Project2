package com.example.ajiekc.project2;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by alexs on 10/10/2016.
 */

public class Timetable_Model implements Serializable {

    public String activeDay;
    public Map<String,String> timetable;
    public String activeWeeekday;


    public Timetable_Model() {}

    public Timetable_Model(String activeDay, Map<String,String> timetable,
                           String activeWeeekday)
    {
        this.activeDay = activeDay;
        this.activeWeeekday = activeWeeekday;
        this.timetable = timetable;
    }
}
