package com.example.ajiekc.project2;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by AJIEKC on 01.10.2016.
 */
public class Parsing {

    private String html;
    private String student_name;
    private Map<String,String> subjects;
    private String activeYear;
    private String activeSeason;
    private String activeGroup;
    private String educationName;
    private Map<String,String> years;


    private String activeDay;
    private ArrayList<String> timetable;
    private String activeWeeekday;



    private Context context;

    Parsing()
    {
        html = "";
    }

    Parsing(String html, Context context) { this.html = html; this.context = context; }

    /*private boolean SaveData()
    {
        try {
            FileOutputStream fos = context.openFileOutput("BRS_Model", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(new BRS_Model(student_name,subjects,activeYear,activeGroup,activeSeason,educationName,years));
            os.close();
            fos.close();
            return true;
        }
        catch (Exception e){ Log.e("test",e.toString()); return false;}

    }*/
    public boolean LoadData()
    {
        try {
            FileInputStream fis = context.openFileInput("BRS_Model");
            ObjectInputStream is = new ObjectInputStream(fis);
            BRS_Model brs = (BRS_Model) is.readObject();
            is.close();
            fis.close();
            Log.i("test", "Load data completed!");
            Log.i("test", brs.student_name);
            Log.i("test", brs.activeYear);
            Log.i("test", brs.activeGroup);
            Log.i("test", brs.activeSeason);
            Log.i("test", brs.educationName);
            for (Map.Entry entry : brs.subjects.entrySet()) {
                Log.i("test", entry.getKey().toString());
                Log.i("test", entry.getValue().toString());
            }
            for (Map.Entry entry : brs.years.entrySet()) {
                Log.i("test", entry.getKey().toString());
                Log.i("test", entry.getValue().toString());
            }
        }
        catch (Exception e){
            Log.e("test",e.toString());
            return false;
        }
        return true;
    }

    public boolean ParsBRS()
    {
        Document doc = Jsoup.parse(html);
        Elements names = doc.getElementsByClass("stdname");
        student_name = names.first().text();

        Elements edu = doc.getElementsByClass("name-of-edication");
        educationName = edu.first().text();

        Elements subjs = doc.getElementsByClass("js-service-rating-item");
        subjects = new LinkedHashMap<>();
        for(Element s : subjs)
        {
            Element link = s.getElementsByTag("a").first();
            subjects.put(s.text(),link.attr("href"));
        }

        Elements sb = doc.getElementsByClass("sectiononbrs");

        Elements vs = sb.first().getElementsByClass("valueshow");
        activeYear = vs.eq(0).text();
        activeGroup = vs.eq(1).text();
        activeSeason = vs.eq(2).text();

        years = new LinkedHashMap<>();
        Elements vl = sb.first().getElementsByClass("values-list");
        Elements links = vl.eq(0).first().getElementsByTag("a");
        for(Element el : links)
            years.put(el.text(),el.attr("href"));

        return SaveData();
    }

    private boolean SaveData()
    {
        try {
            FileOutputStream fos = context.openFileOutput("Timetable_Model", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(new Timetable_Model(activeDay, activeWeeekday, timetable));
            os.close();
            fos.close();
            return true;
        }
        catch (Exception e){ Log.e("test",e.toString()); return false;}
    }

    public boolean LoadData2()
    {
        try {
            FileInputStream fis = context.openFileInput("Timetable_Model");
            ObjectInputStream is = new ObjectInputStream(fis);
            Timetable_Model tTabl = (Timetable_Model) is.readObject();
            is.close();
            fis.close();
            Log.i("test", "Load data completed!");
            Log.i("test", tTabl.activeDay);
            Log.i("test", tTabl.activeWeeekday);
            for (String entry : tTabl.timetable) {
                Log.i("test", entry);
            }
        }
        catch (Exception e){
            Log.e("test",e.toString());
            return false;
        }
        return true;
    }

    ArrayList<Timetable_Model> list = new ArrayList<Timetable_Model>();

    public boolean ParsTimetable()
    {
        Document doc = Jsoup.parse(html);

        Elements date = doc.getElementsByClass("training-schedule");

        for(Element lol : date)
        {
            Elements vl = lol.getElementsByClass("training-schedule-cont");

            Elements vs = lol.getElementsByClass("date");
            activeDay = vs.eq(0).text();
            //Log.i("test",activeDay);

            Elements dd = vl.first().getElementsByClass("day-on-week");
            activeWeeekday = dd.eq(0).text();
            //Log.i("test",activeWeeekday);

            timetable = new ArrayList<>();
            Elements links = vl.eq(0).first().getElementsByTag("tr");
            for(Element el : links)
                timetable.add(el.text());

            list.add(new Timetable_Model(activeDay, activeWeeekday, timetable));
        }

        int in = list.size();
        Log.i("test", String.valueOf(in));
        for (Timetable_Model ss : list)
        {
            Log.i("test",ss.activeDay);
            Log.i("test", ss.activeWeeekday);
            for (String e : ss.timetable)
                Log.i("test", e);
        }

        return SaveData();
    }
}