package com.example.ajiekc.project2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by alexs on 10/10/2016.
 */

public class Timetable_Model implements Serializable {

    public String activeDay;
    public ArrayList<String> timetable;
    public String activeWeeekday;

    public Timetable_Model() {}

    public Timetable_Model(String activeDay, String activeWeeekday, ArrayList<String> timetable)
    {
        this.activeDay = activeDay;
        this.activeWeeekday = activeWeeekday;
        this.timetable = timetable;
    }
}
