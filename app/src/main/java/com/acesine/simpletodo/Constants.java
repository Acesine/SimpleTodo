package com.acesine.simpletodo;

import android.graphics.Color;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Constants {
    // Priority-color
    public final static LinkedHashMap<String, Integer> PRIORITY_COLOR = new LinkedHashMap<>();
    public final static List<String> PRIORITY = new ArrayList<>();
    static {
        PRIORITY.add("High");
        PRIORITY.add("Medium");
        PRIORITY.add("Low");
        PRIORITY_COLOR.put("High", Color.parseColor("#BFDD0000"));
        PRIORITY_COLOR.put("Medium", Color.parseColor("#BFFFFF00"));
        PRIORITY_COLOR.put("Low", Color.parseColor("#BFB2FF66"));
    }

    public final static DateFormat DATE_FORMAT = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
}
