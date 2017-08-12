package com.acesine.simpletodo.utils;

import android.app.Activity;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.acesine.simpletodo.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {
    private DateTimeUtils() {}

    public static void setupDateTime(Activity activity, Date date) {
        DatePicker dp = (DatePicker) activity.findViewById(R.id.datePicker);
        TimePicker tp = (TimePicker) activity.findViewById(R.id.timePicker);
        tp.setIs24HourView(true);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        dp.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        tp.setHour(c.get(Calendar.HOUR_OF_DAY));
        tp.setMinute(c.get(Calendar.MINUTE));
    }

    public static String getTimeToDue(Date due) {
        Date now = new Date();
        long diff = due.getTime() - now.getTime();
        if (diff <= 0) {
            return "Over due!";
        }
        long days = TimeUnit.MILLISECONDS.toDays(diff);
        long hours = TimeUnit.MILLISECONDS.toHours(diff - TimeUnit.DAYS.toMillis(days));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff - TimeUnit.DAYS.toMillis(days) - TimeUnit.HOURS.toMillis(hours));
        if (days > 0) {
            return String.format(Locale.US, "%d d %d h %d m left", days, hours, minutes);
        } else if (hours > 0) {
            return String.format(Locale.US, "%d h %d m left", hours, minutes);
        } else {
            return String.format(Locale.US, "%d m left", minutes);
        }
    }
}
