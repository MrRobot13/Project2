package com.example.ajiekc.project2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by AJIEKC on 07.10.2016.
 */

public class BRS_Model implements Serializable {

    public String student_name;
    public Map<String,String>subjects;
    public String activeYear;
    public String activeGroup;
    public String activeSeason;
    public String educationName;
    public Map<String,String> years;


    public BRS_Model() {}

    public BRS_Model(String student_name, Map<String,String> subjects,
                     String activeYear, String activeGroup, String activeSeason,
                     String educationName,Map<String,String> years)
    {
        this.student_name = student_name;
        this.subjects = subjects;
        this.activeYear = activeYear;
        this.activeGroup = activeGroup;
        this.activeSeason = activeSeason;
        this.educationName = educationName;
        this.years = years;
    }
}
