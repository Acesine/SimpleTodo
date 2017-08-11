package com.acesine.simpletodo;

import android.graphics.Color;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Constants {
    // Priority
    public enum Priority {
        High,
        Medium,
        Low,
        Done
    }
    public final static LinkedHashMap<Priority, Integer> PRIORITY_COLOR = new LinkedHashMap<>();
    public final static List<Priority> PRIORITY = Arrays.asList(Priority.values());
    static {
        PRIORITY_COLOR.put(Priority.High, Color.parseColor("#BFDD0000"));
        PRIORITY_COLOR.put(Priority.Medium, Color.parseColor("#BFFFFF00"));
        PRIORITY_COLOR.put(Priority.Low, Color.parseColor("#BFB2FF66"));
        PRIORITY_COLOR.put(Priority.Done, Color.parseColor("#BFEEEEEE"));
    }

    public final static DateFormat DATE_FORMAT = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
}
