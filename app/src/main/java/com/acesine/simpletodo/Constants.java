package com.acesine.simpletodo;

import android.graphics.Color;

import java.util.LinkedHashMap;
import java.util.Map;

public class Constants {
    // Priority-color
    public final static LinkedHashMap<String, Integer> PRIORITY_COLOR = new LinkedHashMap<>();
    static {
        PRIORITY_COLOR.put("High", Color.parseColor("#BFDD0000"));
        PRIORITY_COLOR.put("Medium", Color.parseColor("#BFFFFF00"));
        PRIORITY_COLOR.put("Low", Color.parseColor("#BFB2FF66"));
    }
}
